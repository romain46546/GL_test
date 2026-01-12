package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;

import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tree.AbstractProgram;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl24
 * @date 01/01/2026
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) throws DecacFatalError, ContextualError {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            System.out.println("GL 24 :" +
                    " ARZEL Romain (arzelr)" +
                    " BEN ABDERRAHMEN Feriel (benabdef)" +
                    " BOUDAHMANE Selyan (boudahms)" +
                    " IHADDADENE Djibril (ihaddadd)" +
                    " PAUCOD Lucas (paucodl)");
            System.exit(0);
        }
        if (options.getSourceFiles().isEmpty()) {
            System.out.println("""
                    decac [[-p | -v] [-n] [-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]
                    -b (banner) : affiche une bannière indiquant le nom de l'équipe
                    -p (parse) : arrête decac après l'étape de construction de l'arbre,\s
                                 et affiche la décompilation de ce dernier\s
                                 (i.e. s'il n'y a qu'un fichier source à compiler, la sortie doit être un programme 
                                 deca syntaxiquement correct)
                    -v (verification) : arrête decac après l'étape de vérifications 
                                        (ne produit aucune sortie en l'absence d'erreur)
                    -n (no check) : supprime les tests à l'exécution spécifiés dans 
                                    les points 11.1 et 11.3 de la sémantique de Deca.
                    -r X (registers) : limite les registres banalisés disponibles à R0 ... R{X-1}, avec 4 <= X <= 16
                    -d (debug) : active les traces de debug. Répéter l'option plusieurs fois pour avoir plus de traces.
                    -P (parallel) : s'il y a plusieurs fichiers sources, 
                                    lance la compilation des fichiers en parallèle (pour accélérer la compilation)""");
            System.exit(0);
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (options.getDecompile()){
                    AbstractProgram prog = compiler.doLexingAndParsing(source.getAbsolutePath(), System.err);
                    System.out.println(prog.decompile());
                } else if (options.getVerify()) {
                    try {
                        AbstractProgram prog = compiler.doLexingAndParsing(source.getAbsolutePath(), System.err);
                        prog.verifyProgram(compiler);
                    } catch (ContextualError e) {
                        e.display(System.err);
                        error = true;
                    }
                } else if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
