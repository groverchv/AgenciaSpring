package com.AgenciaSpring.AgenciaSpring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class BlockchainService {
    private static final Logger logger = Logger.getLogger(BlockchainService.class.getName());
    private final RestTemplate restTemplate;

    @Value("${blockchain.node.url}")
    private String blockchainUrl;

    public BlockchainService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Submits a system-level transaction to the blockchain network.
     */
    public boolean registrarTransaccion(String recipient, Object data) {
        try {
            String url = blockchainUrl + "/api/blockchain/transaction";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", "SYSTEM");
            payload.put("recipient", recipient);
            payload.put("data", data);
            payload.put("signature", "SYSTEM_SPRING_SIGNATURE");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Transacción registrada con éxito en el blockchain para destinatario: " + recipient);
                
                // Intentar minar el bloque automáticamente para procesar la transacción
                minarBloque();
                return true;
            } else {
                logger.severe("Fallo al registrar transacción en blockchain: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            logger.severe("Error de conexión con el nodo blockchain: " + e.getMessage());
            return false;
        }
    }

    /**
     * Triggers mining on the blockchain node to commit the pending transactions.
     */
    private void minarBloque() {
        try {
            String url = blockchainUrl + "/api/blockchain/mine";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();
            payload.put("rewardAddress", "SYSTEM_SPRING_MINER");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Bloque minado con éxito en el blockchain.");
            } else {
                logger.warning("Fallo al solicitar el minado de bloque.");
            }
        } catch (Exception e) {
            logger.warning("Error de conexión al intentar minar bloque: " + e.getMessage());
        }
    }
}
