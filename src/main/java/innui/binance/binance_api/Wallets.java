package innui.binance.binance_api;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.spot.Wallet;
import innui.Bases;
import static innui.binance.binance_api.Clients.k_type_spot;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author emilio
 */
public class Wallets extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Wallets.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public String type = k_type_spot;
    public Clients client;
    public Wallet wallet;
    public Jsons json;
    
    /**
     * Inits the object with mandatory objects
     * @param _client
     * @param _type
     * @param _json
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean init(Clients _client, String _type, Jsons _json, Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        try {
            client = _client;
            type = _type;
            json = _json;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    /**
     * Create wallet attribute
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_wallet(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        try {
            wallet = client.spotClient.createWallet();
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    
    public LinkedHashMap <String, Object> do_account_snapshot(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return null; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        LinkedHashMap <String, Object> retorno = null;
        try {
            if (client.is_spotClient_testnet) {
                ok.setTxt(Tr.in(in, "Not available for testnet"), extra_array);
                if (ok.is == false) { return null; }
            }
            if (wallet == null) {
                create_wallet(ok, extra_array);
                if (ok.is == false) { return null; }
            }
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("type", type);
            String text = wallet.accountSnapshot(parameters);
            LinkedHashMap <String, Object> data_map;
            retorno = json.convert_to_map(text, ok, extra_array);
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
    public LinkedHashMap <String, Object> do_asset_detail(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return null; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        LinkedHashMap <String, Object> retorno = null;
        try {
            if (client.is_spotClient_testnet) {
                ok.setTxt(Tr.in(in, "Not available for testnet"), extra_array);
                if (ok.is == false) { return null; }
            }
            if (wallet == null) {
                create_wallet(ok, extra_array);
                if (ok.is == false) { return null; }
            }
            Map<String, Object> parameters = new LinkedHashMap<>();
            String text = wallet.assetDetail(parameters);
            retorno = json.convert_to_map(text, ok, extra_array);
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }

}
