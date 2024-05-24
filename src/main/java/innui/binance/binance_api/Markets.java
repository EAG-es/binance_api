package innui.binance.binance_api;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.spot.Market;
import static innui.binance.binance_api.Clients.k_type_spot;
import innui.modelos.errors.Oks;
import java.io.File;

/**
 *
 * @author emilio
 */
public class Markets {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Markets.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public String type = k_type_spot;
    public Clients client;
    public Market market;
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
     * Init market attribute
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_market(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        try {
            market = client.spotClient.createMarket();
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    /**
     * Test connection
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String ping(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return null; }
//        ResourceBundle in = null;
//        in = ResourceBundles.getBundle(k_in_route);
        String retorno = null;
        try {
            if (market == null) {
                create_market(ok, extra_array);
                if (ok.is == false) { return null; }
            }
            retorno = market.ping();
        } catch (BinanceClientException binanceClientException) {
            ok.setTxt(binanceClientException, " HttpStatusCode: " + binanceClientException.getHttpStatusCode());
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
}
