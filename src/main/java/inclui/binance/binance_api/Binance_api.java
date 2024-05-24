package inclui.binance.binance_api;

import innui.binance.binance_api.Clients;
import static innui.binance.binance_api.Clients.k_type_spot;
import innui.binance.binance_api.Jsons;
import innui.binance.binance_api.Markets;
import innui.binance.binance_api.Trades;
import innui.binance.binance_api.Wallets;
import innui.binance.binance_api.Yamls;
import innui.modelos.Modelos;
import innui.modelos.configurations.Initials;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import static java.lang.System.exit;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * @author emilio
 */
public class Binance_api extends Initials {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Binance_api.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public static String k_cmdLineSyntax = "cmdLineSyntax"; //NOI18N
//    The following base endpoints are available:
//    https://api.binance.com
//    https://api-gcp.binance.com
//    https://api1.binance.com
//    https://api2.binance.com
//    https://api3.binance.com
//    https://api4.binance.com
//    The last 4 endpoints in the point above (api1-api4) might give better performance but have less stability. Please use whichever works best for your setup.
//    All endpoints return either a JSON object or array.
//    Data is returned in ascending order. Oldest first, newest last.
//    All time and timestamp related fields are in milliseconds.
//    The base endpoint https://data-api.binance.vision can be used to access the following API endpoints that have NONE as security type:
//    GET /api/v3/aggTrades
//    GET /api/v3/avgPrice
//    GET /api/v3/depth
//    GET /api/v3/exchangeInfo
//    GET /api/v3/klines
//    GET /api/v3/ping
//    GET /api/v3/ticker
//    GET /api/v3/ticker/24hr
//    GET /api/v3/ticker/bookTicker
//    GET /api/v3/ticker/price
//    GET /api/v3/time
//    GET /api/v3/trades
//    GET /api/v3/uiKlines    
//
//    Spot API URL                              Spot Test Network URL
//    https://api.binance.com                   https://testnet.binance.vision
//    wss://ws-api.binance.com/ws-api/v3	wss://testnet.binance.vision/ws-api/v3
//    wss://stream.binance.com:9443/ws          wss://testnet.binance.vision/ws
//    wss://stream.binance.com:9443/stream	wss://testnet.binance.vision/stream
    
    public static String k_binance_endpoint = "binance.endpoint";
    public static String k_binance_ws_endpoint = "binance.ws_endpoint";
    public static String k_binance_api_key = "binance.api_key";
    public static String k_binance_secret_key = "binance.secret_key";
    public static int k_line_width = 120;
    public Clients client = new Clients();
    public Jsons json = new Jsons();
    public Yamls yaml = new Yamls();
    public Wallets wallet;
    public Markets market;
    public Trades trade;
    // Apache Commons CLI
    public Options options = new Options();
    public CommandLineParser parser = new DefaultParser();
    public Option option_api_key;
    public Option option_create_spot_client;
    public Option option_create_web_socket_client;
    public Option option_endpoint;
    public Option option_market_ping;
    public Option option_secret_key;
    public Option option_trade_account;
    public Option option_trade_account_key;
    public Option option_wallet_account_snapshot;
    public Option option_wallet_asset_detail;
    public Option option_web_socket_endpoint;
    public LinkedHashMap <String, Object> trade_account_map;    
    /**
     * Writes CLUI help information
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean write_help(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        this.write_line(ok.getTxt(), ok);
        HelpFormatter helpFormatter = new HelpFormatter();
        String cmdLineSyntax = this.properties.getProperty(k_cmdLineSyntax);
        helpFormatter.setWidth(k_line_width);
        helpFormatter.printHelp(cmdLineSyntax, options);
        return ok.is;
    }
    public static void main(String[] args) {
        Oks ok = new Oks();
        try {
            Binance_api binance_api;
            binance_api = new Binance_api();
            binance_api.run(ok, (Object []) args);
        } catch (Exception e) {
            ok.setTxt(e);
        }
        if (ok.is == false) {
            System.err.println(ok.txt);
            exit(1);
        }
    }
    
    @Override
    public boolean run(Oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.is == false) { return false; }
            ResourceBundle in = null;
            // Prepare models: persistent properties (re), internationalization (in),...
            init(ok);
            if (ok.is) { 
                // Get internationalization bundle
                in = ResourceBundles.getBundle(k_in_route);
                while (true) {
                    String [] args = (String []) extras_array;
                    create_options(ok);
                    if (ok.is == false) { break; }
                    process_options_received(args, ok);
                    if (ok.is == false) { break; }
                    break;
                }
                Oks ok_fin = new Oks();
                terminate(ok_fin);
                if (ok_fin.is == false) {
                    ok.setTxt(ok.getTxt(), ok_fin.getTxt());
                }
            }
            return ok.is;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean init(Oks ok, Object... extra_array) throws Exception {
        // Init models for the clases
        // Models: persistent properties (re), internationalization (in),...
        if (ok.is == false) { return ok.is; }
        _init_from_class(Modelos.class, ok);
        if (ok.is == false) { return ok.is; }
        _init_from_class(this.getClass(), ok);
        return ok.is;
    }
    
    @Override
    public boolean terminate(Oks ok, Object... extra_array) throws Exception {
        // Terminate models for the clases
        // Models: persistent properties (re), internationalization (in),...
        if (ok.is == false) { return ok.is; }
        _terminate_from_class(Modelos.class, ok);
        if (ok.is == false) { return ok.is; }
        _terminate_from_class(this.getClass(), ok);
        return ok.is;
    }
    
    /**
     * Creates the CLUI options
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_options(Oks ok, Object ... extra_array) throws Exception {
        ResourceBundle in = null;
        try {
            if (ok.is == false) { return false; }
            in = ResourceBundles.getBundle(k_in_route);
            // A
            option_api_key = Option.builder("ak")
                         .longOpt("api_key")
                         .hasArg()
                         .argName(Tr.in(in, "API key"))
                         .desc(Tr.in(in, "Api key parameter"))
                         .build();
            options.addOption(option_api_key);
            // C
            option_create_spot_client = Option.builder("csc")
                         .longOpt("create_spot_client")
                         .desc(Tr.in(in, "Creates a Spot client"))
                         .build();
            options.addOption(option_create_spot_client);
            option_create_web_socket_client = Option.builder("cwsc")
                         .longOpt("create_web_socket_client")
                         .desc(Tr.in(in, "Creates a Websocket client"))
                         .build();
            options.addOption(option_create_web_socket_client);
            // E
            option_endpoint = Option.builder("ep")
                         .longOpt("end_point")
                         .hasArg()
                         .argName(Tr.in(in, "End point URL (HTTPS)"))
                         .desc(Tr.in(in, "End point URL (conection)"))
                         .build();
            options.addOption(option_endpoint);
            // M
            option_market_ping = Option.builder("mp")
                         .longOpt("market_ping")
                         .desc(Tr.in(in, "Market ping"))
                         .build();
            options.addOption(option_market_ping);
            // S
            option_secret_key = Option.builder("sk")
                         .longOpt("secret_key")
                         .hasArg()
                         .argName(Tr.in(in, "Secret key"))
                         .desc(Tr.in(in, "Secret key parameter"))
                         .build();
            options.addOption(option_secret_key);
            // T
            option_trade_account = Option.builder("ta")
                         .longOpt("trade_account")
                         .hasArg()
                         .argName(Tr.in(in, "key to get (optional)"))
                         .optionalArg(true)
                         .desc(Tr.in(in, "Trade account information"))
                         .build();
            options.addOption(option_trade_account);
            option_trade_account_key = Option.builder("tak")
                         .longOpt("trade_account_key")
                         .hasArg()
                         .argName(Tr.in(in, "Compund key to get (separated by: .)"))
                         .desc(Tr.in(in, "Get a compound key from the trade account information"))
                         .build();
            options.addOption(option_trade_account_key);
            // W
            option_wallet_account_snapshot = Option.builder("was")
                         .longOpt("wallet_account_snapshot")
                         .desc(Tr.in(in, "Wallet account snapshot"))
                         .build();
            options.addOption(option_wallet_account_snapshot);
            option_wallet_asset_detail = Option.builder("wad")
                         .longOpt("wallet_asset_detail")
                         .desc(Tr.in(in, "Wallet asset detail"))
                         .build();
            options.addOption(option_wallet_asset_detail);            
            option_web_socket_endpoint = Option.builder("wsep")
                         .longOpt("web_socket_end_point")
                         .hasArg()
                         .argName(Tr.in(in, "WebSocket end point URL (WSS)"))
                         .desc(Tr.in(in, "Web Socket end point URL (WS conection)"))
                         .build();
            options.addOption(option_web_socket_endpoint);
        } catch (Exception e) {
            throw e;
        }
        return ok.is;
    }
    /**
     * Process CLUI options
     * @param args
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean process_options_received(String [] args, Oks ok, Object ... extra_array) throws Exception {
        ResourceBundle in = null;
        try {
            if (ok.is == false) { return false; }
            in = ResourceBundles.getBundle(k_in_route);
            while (true) {
                CommandLine commandLine = parser.parse(options, args);
                if (commandLine.getArgList().isEmpty() == false) {
                    this.write_line(Tr.in(in, "Unrecognized args"), ok);                
                    if (ok.is == false) { break; }
                    for (String text: commandLine.getArgList()) {
                        this.write_line(text, ok);                
                        if (ok.is == false) { break; }
                    }
                    write_help(ok);
                    if (ok.is == false) { break; }
                } else {
                    for (Option option: commandLine.getOptions()) {
                        process_option(commandLine, option, ok.init());
                        if (ok.is == false) { break; }
                    }
                }
                break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ok.setTxt(e.getLocalizedMessage(), Tr.in(in, "Missing parameters"));
        } catch (Exception e) {
            ok.setTxt(e.getLocalizedMessage());
        }
        if (ok.is == false) {
            this.write_line(ok.getTxt(), ok);
            ok.init();
            write_help(ok);
        }
        return ok.is;
    }    
    /**
     * Process CLUI options
     * @param commandLine
     * @param option
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean process_option(CommandLine commandLine, Option option, Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in = null;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            // A
            if (option.getOpt().equals(option_api_key.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                String api_key = commandLine.getOptionValue(option_api_key);
                properties.setProperty(k_binance_api_key, api_key);
                this.write_line(Tr.in(in, "API key changed"), ok);
                if (ok.is == false) { return false; }
            }
            // C
            else if (option.getOpt().equals(option_create_spot_client.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                String api_key = properties.getProperty(k_binance_api_key);
                String secret_key = properties.getProperty(k_binance_secret_key);
                String base_url = properties.getProperty(k_binance_endpoint);
                if (client.spotClient != null) {
                    client.spotClient = null;
                    wallet = null;
                    this.write_line(Tr.in(in, "Existing spot client is going to be replaced"), ok);
                    if (ok.is == false) { return false; }
                }
                client.create_SpotClient(api_key, secret_key, base_url, ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(Tr.in(in, "Spot client created"), ok);
                if (ok.is == false) { return false; }                
            } else if (option.getOpt().equals(option_create_web_socket_client.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                String api_key = properties.getProperty(k_binance_api_key);
                String secret_key = properties.getProperty(k_binance_secret_key);
                String base_ws_url = properties.getProperty(k_binance_ws_endpoint);
                if (client.webSocketApiClient != null) {
                    client.webSocketApiClient = null;
                    this.write_line(Tr.in(in, "Existing web socket client is going to be replaced"), ok);
                    if (ok.is == false) { return false; }
                }
                client.create_WebSocketApiClient(api_key, secret_key, base_ws_url, ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(Tr.in(in, "Web Socket client created"), ok);
                if (ok.is == false) { return false; }                
            }
            // E
            else if (option.getOpt().equals(option_endpoint.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                String endpoint = commandLine.getOptionValue(option_endpoint);
                properties.setProperty(k_binance_endpoint, endpoint);
                this.write_line(Tr.in(in, "Endpoint changed"), ok);
                if (ok.is == false) { return false; }
            }
            // M
            else if (option.getOpt().equals(option_market_ping.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (client.spotClient == null) {
                    this.write_line(Tr.in(in, "Create Spot Client option is required"), ok);
                    if (ok.is == false) { return false; }
                }
                if (market == null) {
                    market = new Markets();
                    market.init(client, k_type_spot, json, ok, extra_array);
                    if (ok.is == false) { return false; }
                }
                String text = market.ping(ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(text, ok);
                if (ok.is == false) { return false; }                
            }
            // S
            else if (option.getOpt().equals(option_secret_key.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                String secret_key = commandLine.getOptionValue(option_secret_key);
                properties.setProperty(k_binance_secret_key, secret_key);
                this.write_line(Tr.in(in, "Secret key changed"), ok);
                if (ok.is == false) { return false; }
            }
            // T
            else if (option.getOpt().equals(option_trade_account.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                if (client.spotClient == null) {
                    this.write_line(Tr.in(in, "Create Spot Client option is required"), ok);
                    if (ok.is == false) { return false; }
                }
                if (trade == null) {
                    trade = new Trades();
                    trade.init(client, k_type_spot, json, ok, extra_array);
                    if (ok.is == false) { return false; }
                }
                trade_account_map = trade.do_account(ok, extra_array);
                String yaml_tex = yaml.do_to_string(trade_account_map, ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(yaml_tex, ok);
                if (ok.is == false) { return false; }                
            }
            else if (option.getOpt().equals(option_trade_account_key.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                if (client.spotClient == null) {
                    this.write_line(Tr.in(in, "Create Spot Client option is required"), ok);
                    if (ok.is == false) { return false; }
                }
                if (trade == null) {
                    trade = new Trades();
                    trade.init(client, k_type_spot, json, ok, extra_array);
                    if (ok.is == false) { return false; }
                }
                String key_to_get = commandLine.getOptionValue(option_trade_account_key, "");
                if (trade_account_map == null) {
                    trade_account_map = trade.do_account(ok, extra_array);
                }
                Object object = json.access_key(trade_account_map, key_to_get, "\\.", ok, extra_array);
                if (ok.is == false) { return false; }                
                String yaml_tex = yaml.do_to_string(object, ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(yaml_tex, ok);
                if (ok.is == false) { return false; }                
            }
            // W
            else if (option.getOpt().equals(option_wallet_asset_detail.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (client.spotClient == null) {
                    this.write_line(Tr.in(in, "Create Spot Client option is required"), ok);
                    if (ok.is == false) { return false; }
                }
                if (wallet == null) {
                    wallet = new Wallets();
                    wallet.init(client, k_type_spot, json, ok, extra_array);
                    if (ok.is == false) { return false; }
                }
                LinkedHashMap <String, Object> returned_map = wallet.do_asset_detail(ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(returned_map.toString(), ok);
                if (ok.is == false) { return false; }                
            } else if (option.getOpt().equals(option_wallet_account_snapshot.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (client.spotClient == null) {
                    this.write_line(Tr.in(in, "Create Spot Client option is required"), ok);
                    if (ok.is == false) { return false; }
                }
                if (wallet == null) {
                    wallet = new Wallets();
                    wallet.init(client, k_type_spot, json, ok, extra_array);
                    if (ok.is == false) { return false; }
                }
                LinkedHashMap <String, Object> returned_map = wallet.do_account_snapshot(ok, extra_array);
                if (ok.is == false) { return false; }
                this.write_line(returned_map.toString(), ok);
                if (ok.is == false) { return false; }                
            } else if (option.getOpt().equals(option_web_socket_endpoint.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                String web_socket_endpoint = commandLine.getOptionValue(option_web_socket_endpoint);
                properties.setProperty(k_binance_ws_endpoint, web_socket_endpoint);
                this.write_line(Tr.in(in, "Web Socket endpoint changed"), ok);
                if (ok.is == false) { return false; }
            } else {
                ok.setTxt(Tr.in(in, "No options"));
            }
        } catch (Exception e) {
            ok.setTxt(e.getLocalizedMessage());
        }
        return ok.is;
    }

}
