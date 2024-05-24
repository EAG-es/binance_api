package inclui.binance.binance_api;

import innui.modelos.errors.Oks;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author emilio
 */
public class Binance_apiTest {
    
    public Binance_apiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of write_help method, of class Binance_api.
     */
    @Ignore
    public void testWrite_help() throws Exception {
        System.out.println("write_help");
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.write_help(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Binance_api.
     */
    @Ignore
    public void testMain_create_spot_client() {
        System.out.println("main");
        String[] args = {
            "-csc"
        };
        Binance_api.main(args);
    }

    @Ignore
    public void testMain_create_web_socket_client() {
        System.out.println("main");
        String[] args = {
            "-wsep", "wss://testnet.binance.vision/ws-api/v3",
            "-ak", "SfD5SE0JqLvDj9BFRTcpld9NJqP3Jf4OnbS4koYEhKJI6k30wALLT17vapU2Atwt",
            "-sk", "4OfEtBOinaD4cpD469WNMPSQGSYcUfhG5aJnNfxX82LTmE5T9t4jBpPuAmU5MCQ7",
            "-cwsc",
            "-as"
        };
        Binance_api.main(args);
    }

    @Ignore
    public void testMain_market_ping() {
        System.out.println("main");
        String[] args = {
            "-csc",
            "-mp"
        };
        Binance_api.main(args);
    }
    
    @Ignore
    public void testMain_wallet_asset_detail() {
        System.out.println("main");
        String[] args = {
            "-csc",
            "-wad"
        };
        Binance_api.main(args);
    }

    @Test
    public void testMain_trade_account() {
        System.out.println("main");
        String[] args = {
            "-csc",
            "-ta",
            "-tak",
            "balances.3"
        };
        Binance_api.main(args);
    }

    @Ignore
    public void testMain_wallet_account_snapshot() {
        System.out.println("main");
        String[] args = {
            "-csc",
            "-was"
        };
        Binance_api.main(args);
    }
    /**
     * Test of run method, of class Binance_api.
     */
    @Ignore
    public void testRun() throws Exception {
        System.out.println("run");
        Oks ok = null;
        Object[] extras_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.run(ok, extras_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class Binance_api.
     */
    @Ignore
    public void testInit() throws Exception {
        System.out.println("init");
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.init(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of terminate method, of class Binance_api.
     */
    @Ignore
    public void testTerminate() throws Exception {
        System.out.println("terminate");
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.terminate(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create_options method, of class Binance_api.
     */
    @Ignore
    public void testCreate_options() throws Exception {
        System.out.println("create_options");
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.create_options(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process_options_received method, of class Binance_api.
     */
    @Ignore
    public void testProcess_options_received() throws Exception {
        System.out.println("process_options_received");
        String[] args = null;
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.process_options_received(args, ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process_option method, of class Binance_api.
     */
    @Ignore
    public void testProcess_option() throws Exception {
        System.out.println("process_option");
        CommandLine commandLine = null;
        Option option = null;
        Oks ok = null;
        Object[] extra_array = null;
        Binance_api instance = new Binance_api();
        boolean expResult = false;
        boolean result = instance.process_option(commandLine, option, ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
