package io.blueharvest.ams.common.apisec;

public interface ApiKeyValidator {

    void validateKey(String apiKey) throws InvalidApiKeyException;
}
