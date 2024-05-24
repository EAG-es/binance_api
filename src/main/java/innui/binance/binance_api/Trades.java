package innui.binance.binance_api;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.spot.Trade;
import static innui.binance.binance_api.Clients.k_type_spot;
import static innui.binance.binance_api.Wallets.k_in_route;
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
public class Trades {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Trades.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public String type = k_type_spot;
    public Clients client;
    public Trade trade;
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
     * Creates trade attribute
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_trade(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        try {
            trade = client.spotClient.createTrade();
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    /**
     * 
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public LinkedHashMap <String, Object> do_account(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return null; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        LinkedHashMap <String, Object> retorno = null;
        try {
            if (trade == null) {
                create_trade(ok, extra_array);
                if (ok.is == false) { return null; }
            }
            Map<String, Object> parameters = new LinkedHashMap<>();
            String text = trade.account(parameters);
            retorno = json.convert_to_map(text, ok, extra_array);
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
}
