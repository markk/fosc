/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscPersistenceManager


SUMMARY:: Returns a FoscPersistenceManager.


DESCRIPTION:: Persistence manager


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscPersistenceManager : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <client;
    var pngPagePattern=".+page(\\d+)\.png";
    *new { |client|
        ^super.new.init(client);
    }
    init { |argClient|
        client = argClient;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • asLY

    Persists client as LilyPond file.

    Autogenerates file path when 'path' is nil.

    Returns output path and elapsed formatting time when LilyPond output is written.
    -------------------------------------------------------------------------------------------------------- */
    asLY { |path, illustrateFunction ... args|
        var illustration, lyFileName, lyFile;
        if (illustrateFunction.isNil) {
            illustrateFunction = client.illustrate;
        };
        illustration = illustrateFunction.format; //!!!TODO: temporary
        if (path.isNil) {
            lyFileName = FoscIOManager.nextOutputFileName;
            path = FoscConfiguration.foscOutputDirectory ++ "/" ++ lyFileName;
        };
        lyFile = File(path, "w");
        lyFile.write(illustration);
        lyFile.close;
        ^path;
    }
    /* --------------------------------------------------------------------------------------------------------
    • asMIDI
    -------------------------------------------------------------------------------------------------------- */
    asMIDI {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asPDF

    Persists client as PDF.

    Autogenerates file path when 'lyPath' is nil.

    Returns output path.

    If 'clean' is true, ly file is deleted.

    '''
    code::nointerpret
    a = FoscNote(60, 1/4);
    b = a.write.asPDF;

    post::
    [ /home/user/.config/SuperCollider/fosc-output/0033.pdf, true ]
    '''
    -------------------------------------------------------------------------------------------------------- */
    asPDF { |lyPath, outputPath, illustrateFunction, flags, clean=false ... args|
        var success;
        if (illustrateFunction.isNil) { assert(client.respondsTo('illustrate')) };
        lyPath = this.asLY(lyPath, illustrateFunction, *args);
        outputPath = outputPath ?? { lyPath.splitext[0] };
        success = FoscIOManager.runLilypond(lyPath, flags, outputPath.shellQuote);
        if (success && clean) { File.delete(lyPath) };
        outputPath = outputPath ++ ".pdf";
        ^[outputPath, success];
    }
    /* --------------------------------------------------------------------------------------------------------
    • asPNG

    '''
    code::nointerpret
    a = FoscNote(60, 1/4);
    n = "basic-usage/images/002";
    p = "%/docs/%".format(FoscConfiguration.foscRootDirectory, n);
    f = a.writePNG("%.ly".format(p), p);
    unixCmd("open %".format(f[0]));
    '''
    -------------------------------------------------------------------------------------------------------- */
    asPNG { |lyPath, outputPath, illustrateFunction, resolution=100, clean=true ... args|
        var flags, success;
        if (illustrateFunction.isNil) { assert(client.respondsTo('illustrate')) };
        lyPath = this.asLY(lyPath, illustrateFunction, *args);
        outputPath = outputPath ?? { lyPath.splitext[0] };
        //flags = "-dbackend=eps -dno-gs-load-fonts -dinclude-eps-fonts --png";
        flags = "-dbackend=eps -dresolution=% -dno-gs-load-fonts -dinclude-eps-fonts --png".format(resolution);
        success = FoscIOManager.runLilypond(lyPath, flags, outputPath.shellQuote);
        if (success && clean) {
            #[
                "%-1.eps",
                "%-systems.count",
                "%-systems.tex",
                "%-systems.texi",
                "%.ly"
            ].do { |each| File.delete(each.format(outputPath)) };
        };
        outputPath = (outputPath ++ ".png").shellQuote;
        ^[outputPath, success];
    }
    /* --------------------------------------------------------------------------------------------------------
    • asSVG

    `lilypond -dbackend=svg -dcrop file.ly`
    -------------------------------------------------------------------------------------------------------- */
    asSVG { |lyPath, outputPath, illustrateFunction, crop=true, clean=true ... args|
        // if (crop) { add "-dcrop" to flags string };
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • client

    Gets client of persistence manager.

    Returns component or selection.
    -------------------------------------------------------------------------------------------------------- */
}
