package com.xsustain.xsustaincrm.utility;

import com.nylas.NylasClient;

import java.util.HashMap;
import java.util.Map;

public class NylasConfigManager {

    private static NylasConfigManager instance;
    private Map<String, String> nylasConfig;
    private NylasClient nylasClient;

    // Private constructor to implement Singleton pattern
    private NylasConfigManager() {
        // Load Nylas configuration manually
        nylasConfig = new HashMap<>();
        nylasConfig.put("clientId", "c9314065-1236-412b-a943-9b947170d56b");
        nylasConfig.put("callbackUri", "http://localhost:8085/api/v1/mailing/oauth/exchange");
        nylasConfig.put("apiKey", "nyk_v0_7CozmEfdKwjeurCSBAGFCsGHwZt33qGUCXbLUokgbMra6gJlzzXv6EOn4DFbItWL");
        nylasConfig.put("apiUri", "http://api.us.nylas.com");

        // Initialize NylasClient
        nylasClient = new NylasClient.Builder("nyk_v0_7CozmEfdKwjeurCSBAGFCsGHwZt33qGUCXbLUokgbMra6gJlzzXv6EOn4DFbItWL")
                .apiUri("http://api.us.nylas.com")
                .build();
    }

    // Get the singleton instance of NylasConfigManager
    public static NylasConfigManager getInstance() {
        if (instance == null) {
            instance = new NylasConfigManager();
        }
        return instance;
    }

    // Access to Nylas config map
    public Map<String, String> getNylasConfig() {
        return nylasConfig;
    }

    // Access to Nylas client
    public NylasClient getNylasClient() {
        return nylasClient;
    }
    public String getCallbackUri() {
        return "http://localhost:8085/api/v1/mailing/oauth/exchange"; // Or load from configuration
    }
    
    public String getClientId() {
        return "c9314065-1236-412b-a943-9b947170d56b"; // Or load from configuration
    }
}
