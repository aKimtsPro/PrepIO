package be.akimts.test.trad;

import be.akimts.test.trad.interfaces.MainMenu;
import be.akimts.test.trad.service.ProductService;
import be.akimts.test.trad.service.ProductServiceImpl;
import be.akimts.test.trad.utils.ArgsUtils;
import be.akimts.test.trad.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ProductApp {
    public static void main(String[] args) {
        try {
            Map<String,String> argMap = ArgsUtils.argsToMap(args);
            Map<String,String> config = FileUtils.getConfig();
            Map<String,String> trads = FileUtils.getTrad(
                    argMap.containsKey("traduction") ? argMap.get("traduction") : config.get("traduction"));
            File saveFile = FileUtils.getFile(
                    argMap.containsKey("saveFile") ? argMap.get("saveFile") : config.get("saveFile")
            );
            ProductService productService = new ProductServiceImpl(saveFile);
            new MainMenu(trads,productService).start();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
