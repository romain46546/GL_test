package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl24
 * @date 01/01/2026
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    public boolean getDecompile(){return decompile;}

    public boolean getVerify(){return verify;}

    public boolean getCheck(){return check;}

    public int getRegisters() {return registers;}

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();
    private boolean decompile = false; // variable pour option p
    private boolean verify = false; // variable pour option v
    private boolean check = true; // variable pour option n
    private int registers = 16;

    
    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : faire une implémentation correcte du -P
        for (int i = 0; i < args.length; i++){
            switch (args[i]){
                case "-b" : {
                    printBanner = true;
                    break;
                }
                case "-p" : {
                    decompile = true;
                    break;
                }
                case "-v" : {
                    verify = true;
                    break;
                }
                case "-n" : {
                    check = true;
                    break;
                }
                case "-r" : {
                    i++;
                    if (i >= args.length){throw new CLIException("The option -r need to be given with a number between 4 and 16");}
                    try {
                        registers = Integer.parseInt(args[i]);
                    } catch (NumberFormatException e) {
                        throw new CLIException("The option -r need to be given with a number between 4 and 16");
                    }
                    break;
                }
                case "-d" : {
                    debug += 1;
                    break;
                }
                case "-P" : {
                    parallel = true;
                    break;
                }
                default : {
                    if (args[i].endsWith(".deca")){
                        try {
                            File file = new File(args[i]);
                            sourceFiles.add(file);
                            break;
                        } catch (Exception e) {
                            throw new CLIException("File doesn't exist or wrong path");
                        }
                    } else {
                        throw new CLIException("Argument not recognize : " + args[i]);
                    }
                }
            }
        }
        /*
        Test des différentes conditions d'utilisations lié aux options de decac
         */
        if (args.length > 1 && printBanner){throw new CLIException("-b must be used alone");}
        if (decompile && verify) throw new CLIException("-p and -v cannot be used together");
        if ((decompile || verify || registers != 16) && sourceFiles.isEmpty()){
            throw new CLIException("Missing file");
        }
        if ((4 > registers || registers > 16) && registers != -1){throw new CLIException("The option -r need to be given with a number between 4 and 16");}
        if (debug > 3){throw new CLIException("There no debug level higher than 3");}


        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }
    }

    protected void displayUsage() {
        System.out.println("decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]");
    }
}
