package nl.tudelft.jpacman.points;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The responsibility of this loader is to obtain the appropriate points calculator.
 * By default the {@link DefaultPointCalculator} is returned.
 */
public class PointCalculatorLoader {

    private  PointCalculator clazz = null;

    /**
     * Load a points calculator and return it.
     *
     * @return The (dynamically loaded) points calculator.
     */
    public PointCalculator load() {
        try {
            if (clazz == null) {
                clazz = loadClassFromFile();
            }
        }catch (FileNotFoundException|ClassNotFoundException e){
            Logger.getGlobal().info(e.getMessage());
            System.exit(0);
        }
        return  clazz;
    }

    private PointCalculator loadClassFromFile() throws  FileNotFoundException,ClassNotFoundException {
        String strategyToLoad = getCalculatorClassName();

        if ("DefaultPointCalculator".equals(strategyToLoad)) {
            return new DefaultPointCalculator();
        }else{
            throw new ClassNotFoundException("Invalid pointCalculator:"+ strategyToLoad);
        }
    }

    private String getCalculatorClassName() throws FileNotFoundException {
        Properties properties = new Properties();
    try {
     properties.load(getClass().getClassLoader().getResourceAsStream("scorecalc.properties"));
    }catch(IOException e){
    throw new FileNotFoundException("We could not find the file scorecalc.properties");
    }

        return properties.getProperty("scorecalculator.name");
    }
}
