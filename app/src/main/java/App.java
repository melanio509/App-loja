package atec.poo.loja.app;

import atec.poo.loja.app.exceptions.AppInvalidClientAgeException;
import atec.poo.loja.app.exceptions.AppInvalidProductPriceException;
import atec.poo.loja.app.exceptions.AppInvalidProductStockException;
import atec.poo.loja.app.exceptions.AppInvalidWarrantyMonthsException;
import atec.poo.loja.app.main.Menu;
import atec.poo.loja.core.StoreManager;
import atec.poo.loja.core.exceptions.InvalidClientException;
import atec.poo.loja.core.exceptions.InvalidProductPriceException;
import atec.poo.loja.core.exceptions.InvalidProductStockException;
import atec.poo.loja.core.exceptions.InvalidWarrantyMonthsException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws AppInvalidClientAgeException, AppInvalidWarrantyMonthsException, AppInvalidProductPriceException, AppInvalidProductStockException {
        //CaminhoFerro cf = new CaminhoFerro();
        StoreManager storeManager=new StoreManager();
        //Efetuar Import caso haja
        String dataFile=System.getProperty("import");
        if(dataFile!=null) {
            try {
                storeManager.importFile(dataFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidClientException e) {
                throw new AppInvalidClientAgeException(e.getBirthDate());
            } catch (InvalidWarrantyMonthsException e) {
                throw new AppInvalidWarrantyMonthsException(e.getWarrantyMonths());
            } catch (InvalidProductPriceException e) {
                throw new AppInvalidProductPriceException(e.getPrice());
            } catch (InvalidProductStockException e) {
                throw new AppInvalidProductStockException(e.getStock());
            }
        }
        Menu menu=new Menu(storeManager);
        menu.open();
    }
}
