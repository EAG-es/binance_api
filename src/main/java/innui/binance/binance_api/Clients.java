package innui.binance.binance_api;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.WebSocketApiClient;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebSocketApiClientImpl;
import com.binance.connector.client.utils.signaturegenerator.HmacSignatureGenerator;
import innui.Bases;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import java.util.ResourceBundle;

/**
 *
 * @author emilio
 */
public class Clients extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Clients.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public static String k_type_spot = "SPOT";
    public static String k_type_margin = "MARGIN";
    public static String k_testnet_tex = "testnet";
    public SpotClient spotClient;
    public Boolean is_spotClient_testnet;
    public WebSocketApiClient webSocketApiClient;
    public Boolean is_webSocketApiClient_testnet;
    
    /**
     * Creates a new SpotClient
     * @param api_key
     * @param secret_key
     * @param base_url
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_SpotClient(String api_key, String secret_key, String base_url, Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            if (spotClient == null) {
                is_spotClient_testnet = null;
                spotClient = new SpotClientImpl(api_key, secret_key, base_url);
                if (base_url.toLowerCase().contains(k_testnet_tex.toLowerCase())) {
                    is_spotClient_testnet = true;
                } else {
                    is_spotClient_testnet = false;
                }
            } else {
                ok.setTxt(Tr.in(in, "Already created a SpotClient"), extra_array);
            }
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, "HttpStatusCode=" + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    
    /**
     * Creates a new SpotClient
     * @param api_key
     * @param secret_key
     * @param base_ws_url
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_WebSocketApiClient(String api_key, String secret_key, String base_ws_url, Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            if (webSocketApiClient == null) {
                is_webSocketApiClient_testnet = null;
                HmacSignatureGenerator hmacSignatureGenerator = new HmacSignatureGenerator(secret_key);
                webSocketApiClient = new WebSocketApiClientImpl(api_key, hmacSignatureGenerator, base_ws_url);
                if (base_ws_url.toLowerCase().contains(k_testnet_tex.toLowerCase())) {
                    is_webSocketApiClient_testnet = true;
                } else {
                    is_webSocketApiClient_testnet = false;
                }
            } else {
                ok.setTxt(Tr.in(in, "Already created a WebSocketApiClient"), extra_array);
            }
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, "HttpStatusCode=" + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    
}
