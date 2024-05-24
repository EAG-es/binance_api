package innui.binance.binance_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import innui.Bases;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 *
 * @author emilio
 */
public class Yamls extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Yamls.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public ObjectMapper objectMapper = null;
    
    public boolean _default_builder(Oks ok, Object ... extras_array) throws Exception {
        objectMapper = YAMLMapper.builder()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .disable(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS)
                .enable(YAMLGenerator.Feature.INDENT_ARRAYS)
                .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
                .build();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ (+S'ms')");
        objectMapper.setDateFormat(dateFormat);
        return ok.is;
    }
    
    public <T extends Object> T open_and_read_file(File file, Class<T> _class, Oks ok, Object ... extras_array) throws Exception {
        T retorno = null;
        if (ok.is == false) { return null; }
        ResourceBundle in;
        try {            
            in = ResourceBundles.getBundle(k_in_route);
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return null; }
            }
            if (file.exists() == false) {
                ok.setTxt(Tr.in(in, "File does not exists: ")
                  + file.getCanonicalPath());
            } else {
                if (file.canRead() == false) {
                    ok.setTxt(Tr.in(in, "File does not have read permit: ")
                      + file.getCanonicalPath());
                } 
            }
            if (ok.is == false) { return null; }
            try (InputStream inputStream = new FileInputStream(file)) {
                retorno = objectMapper.readValue(inputStream, _class);
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }    

    public <T extends Object> boolean open_and_write_file(File file, T data, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in;
        try {
            in = ResourceBundles.getBundle(k_in_route);
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return false; }
            }
            if (file.exists() == false) {
                ok.setTxt(Tr.in(in, "File does not exists: ")
                  + file.getCanonicalPath());
            } else {
                if (file.canWrite() == false) {
                    ok.setTxt(ok.getTxt(), Tr.in(in, "File does not have write permit: ")
                      + file.getCanonicalPath());
                }
            }
            if (ok.is == false) { return false; }
            try (OutputStream outputStream = new FileOutputStream(file)) {
                objectMapper.writeValue(outputStream, data);
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.is;
    }    

    public <T extends Object> String do_to_string(T data, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return null; }
            }
            retorno = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }    
}
