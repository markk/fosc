/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscMarkup


SUMMARY:: Returns a FoscMarkup.


DESCRIPTION:: LilyPond markup.


!!!TODO: write a parser - see abjad source

USAGE::

'''
Initialize from string.

code::
m = FoscMarkup("And one and two and three.");
m.format;
m.show;
'''

'''
Initialize from array.

code::
m = FoscMarkup(["\\italic", "Allegro assai"]);
m.format;
'''

'''
Initialize from markup.

code::
m = FoscMarkup("And one and two and three.");
m = FoscMarkup(m);
m.format;
'''

'''
Attach markup to score components.

code::
m = FoscMarkup(["\\italic", "Allegro assai"], 'up');
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a[0].attach(m);
a.show;
'''

'''
Markups can be tweaked.

code::
m = FoscMarkup(["\\italic", "Allegro assai"], 'up');
tweak(m).color = 'blue';
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a[0].attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscMarkup : FoscObject {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <privateAttributesToCopy;
    var <annotation, <contents, <direction, <literal, <tweaks;
    *initClass {
        privateAttributesToCopy = #['tweaks'];
    }
    *new { |contents, direction, literal=false, tweaks|
        var newContents;
        case
        { contents.isNil } {
            newContents = [""];
        }
        //!!!TODO: lilypond parse here
        { [String, Symbol, Number].any { |type| contents.isKindOf(type) } } {
            newContents = [contents.asString]
        }
        //!!!TODO: end lilypond parse
        { contents.isMemberOf(FoscMarkupCommand) } {
            newContents = [contents];
        }
        { contents.isMemberOf(FoscMarkup) } {
            direction = direction ?? { contents.direction };
            newContents = contents.contents;
        }
        { contents.isSequenceableCollection && { contents.size > 0 } } {
            newContents = [];
            contents.do { |each|
                case
                { each.isMemberOf(FoscMarkupCommand) } {
                    newContents = newContents.add(each);
                }
                { each.isMemberOf(FoscMarkup) } {
                    newContents = newContents.addAll(each.contents);
                }
                {
                    newContents = newContents.add(each.asString);
                };
            };
        }
        {
            newContents = [contents.asString];
        };
        assert(newContents.isSequenceableCollection);
        assert(newContents.every { |each| [String, FoscMarkupCommand].any { |type| each.isKindOf(type) } });
    	^super.new.init(newContents, direction, literal, tweaks);
    }
    init { |argContents, argDirection, argLiteral, argTweaks|
        contents = argContents;
        direction = argDirection;
        literal = argLiteral;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • contents

    Gets contents of markup.

    Returns array.

    '''
    code::
    m = FoscMarkup("Allegro");
    m.contents;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • direction

    Gets direction of markup.

    Defaults to none.

    Set to up, down, center or none.

    Returns up, down, center or none.

    '''
    code::
    m = FoscMarkup("Allegro", direction:'-');
    m.direction;
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • literal

    !!!TODO: when parser is written

    Is true when markup formats contents literally.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweak

    Gets tweaks.

    '''
    code::
    m = FoscMarkup("Allegro assai", 'up');
    tweak(m).color = 'blue';
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    a[0].attach(m);
    a.show;
    '''

    '''
    code::
    m = FoscMarkup("Allegro assai", 'up', tweaks: #[['color', 'blue']]);
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    a[0].attach(m);
    a.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • ++ (abjad: __add__)


    Adds markup to 'argument'.
    -------------------------------------------------------------------------------------------------------- */
    ++ { |expr|
    	^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • copy

    Copies markup.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • == (abjad: __eq__)

    Is true markup equals argument.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString

    !!!TODO:INCOMPLETE
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"FoscMarkup(\"%\")".format(contents[0]);
    }
    /* --------------------------------------------------------------------------------------------------------
    • format

    Formats markup.

    Returns string.
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.prGetLilypondFormat;
    }
    /* --------------------------------------------------------------------------------------------------------
    • illustrate

    Illustrates markup.

    Returns LilyPond file.

    '''
    code::
    m = FoscMarkup("foo");
    m.illustrate;
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    illustrate {
    	var lilypondFile, markup;
        lilypondFile = FoscLilypondFile();
        lilypondFile.headerBlock.tagline = false;
        markup = this.species.new(this);
        lilypondFile.items.add(markup.format);
        ^lilypondFile;
    }
    /* --------------------------------------------------------------------------------------------------------
    • <

    Is true when markup contents compare less than 'argument' contents.

    Raises type error when 'argument' is not markup.
    -------------------------------------------------------------------------------------------------------- */
    < { |expr|
    	^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • str (abjad: __str__)

    Gets string representation of markup.

    Returns string.

    '''
    code::
    FoscMarkup("Allegro assai").str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
    	^this.prGetLilypondFormat;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetFormatPieces

    !!!TODO: not being parsed command-by-command to new lines, as in abjad - is a simple parser needed ??

    '''
    code::
    m = FoscMarkup("\\italic Allegro");
    m.format;

    code::
    m = FoscMarkup(["\\italic", "Allegro assai"]);
    m.format;

    code::
    m = FoscMarkup("a bit of text.", direction: 'up');
    m.format;

    code::
    m = FoscMarkup(["a bit of text.", "more text."], direction: 'down');
    m.format;

    code::
    m = FoscMarkup([m, m]);
    m.format;

    code::
    m = FoscMarkup("1").box;
    m = m.override(['box-padding', 0.5]);
    m.format;

    code::
    m = FoscMarkup('foobar');
    m = m.withColor(FoscSchemeColor("ForestGreen"));
    m.prGetFormatPieces.printAll;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetFormatPieces {
        var localTweaks, indent, content, localDirection="", string, pieces;

        if (tweaks.notNil) { localTweaks = tweaks.prListFormatContributions };

        indent = FoscLilypondFormatManager.indent;

        if (direction.notNil) { localDirection = direction.asSymbol.toTridirectionalLilypondSymbol };

        if (contents.size == 1 && { contents[0].isString }) {
            content = contents[0];
            if (literal.not) {
                content = FoscScheme.formatSchemeValue(content);
                if (content.notEmpty) {
                    content = "{ % }".format(content);
                } {
                    content = "{}";
                };
            };
            if (localDirection.notNil) {
                string = "%\\markup %".format(localDirection, content);
                ^(localTweaks ++ [string]);
            } {
                string = "\\markup %".format(content);
                ^(localTweaks ++ [string]);
            };
        };

        if (localDirection.notNil) {
            string = "%\\markup {".format(localDirection);
            pieces = [string];
        } {
            pieces = ["\\markup {"];
        };

        contents.do { |content|
            if (content.isString) {
                content = FoscScheme.formatSchemeValue(content);
                pieces = pieces.add("%%".format(indent, content));
            } {
                content.prGetFormatPieces.do { |each|
                    pieces = pieces.addAll(["%%".format(indent, each)]);
                };
            };
        };
        pieces = pieces.add("}");
        ^(localTweaks ++ pieces);
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
    	^this.prGetFormatPieces.join("\n");
    }
    /* --------------------------------------------------------------------------------------------------------
    • prUpdateExpression
    -------------------------------------------------------------------------------------------------------- */
    prUpdateExpression { |frame|
    	^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prParseMarkupCommandArgument

    '''
    code::
    m = FoscMarkup("Los Angeles");
    x = FoscMarkup.prParseMarkupCommandArgument(m);
    FoscMarkup(x).format;
    '''

    '''
    code::
    m = "Los Angeles";
    x = FoscMarkup.prParseMarkupCommandArgument(m);
    FoscMarkup(x).format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prParseMarkupCommandArgument { |expr|
        var contents;
        case
        { expr.isMemberOf(FoscMarkup) } {
            contents = if (expr.contents.size == 1) { expr.contents[0] } { expr.contents };
        }
        { expr.isString || { expr.isMemberOf(FoscMarkupCommand) } } {
            contents = expr;
        }
        {
            throw("%:%: must be FoscMarkup, FoscMarkupCommand, or String: %.".
                format(this.species, thisMethod.name, expr));
        };
        ^contents;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *abjadMetronomeMark
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • *centerColumn

    LilyPond ``\center-column`` markup command.

    Returns new markup.

    '''
    code::
    a = FoscMarkup("Los Angeles");
    b = FoscMarkup("May - August 2014");
    m = FoscMarkup.centerColumn([a, b]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *centerColumn { |markupList, direction|
        var contents, command;
        contents = [];
        markupList.do { |markup|
            contents = contents.add(FoscMarkup.prParseMarkupCommandArgument(markup));
        };
        command = FoscMarkupCommand('center-column', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • *column

    LilyPond ``\column`` markup command.

    Returns new markup.

    '''
    Example 1

    code::
    a = FoscMarkup("Los Angeles");
    b = FoscMarkup("May - August 2014");
    m = FoscMarkup.column([a, b]);
    m.show;

    img:: ![](../img/markup-markup-8.png)
    '''

    p = "%/fosc/docs/img/markup-markup-8".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *column { |markupList, direction|
        var contents, command;
        contents = [];
        markupList.do { |markup| contents = contents.addAll(markup.contents) };
        command = FoscMarkupCommand('column', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *combine

    LilyPond ``\combine`` markup command.

    Returns new markup.


    Example 1

    code::
    a = FoscMarkup("Allegro assai");
    b = FoscMarkup.drawLine(13, 0);
    m = FoscMarkup.combine([a, b]);
    m.show;

    img:: ![](../img/markup-markup-9.png)
    '''

    p = "%/fosc/docs/img/markup-markup-9".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *combine { |markupList, direction|
        var markup1, markup2, contents1, contents2, command;
        # markup1, markup2 = markupList;
        contents1 = FoscMarkup.prParseMarkupCommandArgument(markup1);
        contents2 = FoscMarkup.prParseMarkupCommandArgument(markup2);
        command = FoscMarkupCommand('combine', contents1, contents2);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *concat !!!TODO

    LilyPond ``\concat`` markup command.

    Returns new markup.


    Example 1

    code::
    a = FoscMarkup.musicGlyph('scripts.downbow');
    b = FoscMarkup.hspace(1);
    c = FoscMarkup.musicGlyph('scripts.upbow');
    m = FoscMarkup.concat([a, b, c]);
    m.show;

    img:: ![](../img/markup-markup-10.png)
    '''

    p = "%/fosc/docs/img/markup-markup-10".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *concat { |markupList, direction|
        var result, contents, command;
        result = [];
        markupList.do { |markup|
            contents = FoscMarkup.prParseMarkupCommandArgument(markup);
            result = result.add(contents);
        };
        command = FoscMarkupCommand('concat', result);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *drawCircle

    LilyPond ``\draw-circle`` markup command.

    Returns new markup


    Example 1

    code::
    m = FoscMarkup.drawCircle(10, 1.5);
    m.show;

    img:: ![](../img/markup-markup-11.png)
    '''

    p = "%/fosc/docs/img/markup-markup-11".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *drawCircle { |radius, thickness, direction, filled=false|
        var command;
        command = FoscMarkupCommand('draw-circle', radius, thickness, filled);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *drawLine

    LilyPond ``\draw-line`` markup command.

    Returns new markup


    Example 1

    code::
    m = FoscMarkup.drawLine(5, -2.5);
    m.show;

    img:: ![](../img/markup-markup-12.png)
    '''

    p = "%/fosc/docs/img/markup-markup-12".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *drawLine { |x, y, direction|
        var pair, command;
        pair = FoscSchemePair(x, y);
        command = FoscMarkupCommand('draw-line', pair);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *filledBox

    LilyPond filled-box markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.filledBox(#[0,10], #[2,5], 1.5);
    m.show;

    img:: ![](../img/markup-markup-13.png)
    '''

    p = "%/fosc/docs/img/markup-markup-13".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *filledBox { |xExtent, yExtent, blot=0, direction|
        var command;
        xExtent = FoscSchemePair(xExtent);
        yExtent = FoscSchemePair(yExtent);
        blot = blot.asFloat;
        command = FoscMarkupCommand('filled-box', xExtent, yExtent, blot);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *flat

    LilyPond ``\flat`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.flat;
    m.show;

    img:: ![](../img/markup-markup-14.png)
    '''

    p = "%/fosc/docs/img/markup-markup-14".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *flat { |direction|
        var command;
        command = FoscMarkupCommand('flat');
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *fromLiteral

    !!!TODO: once parser is written
    '''
    -------------------------------------------------------------------------------------------------------- */
    *fromLiteral {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *fraction

    LilyPond ``\fraction`` markup command.

    Returns new markup


    Example 1

    code::
    m = FoscMarkup.fraction(1, 4);
    m.show;

    img:: ![](../img/markup-markup-15.png)
    '''

    p = "%/fosc/docs/img/markup-markup-15".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));





    Example 2

    code::
    m = FoscMarkup.fraction('π', 4);
    m.show;

    img:: ![](../img/markup-markup-16.png)
    '''

    p = "%/fosc/docs/img/markup-markup-16".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *fraction { |numerator, denominator, direction|
        var command;
        command = FoscMarkupCommand('fraction', numerator.asString, denominator.asString);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *hspace

    LilyPond ``\hspace`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.hspace(0.75);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *hspace { |amount, direction|
        var command;
        command = FoscMarkupCommand('hspace', amount);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *leftColumn

    LilyPond ``\left-column`` markup command.

    Returns new markup.


    Example 1

    code::
    a = FoscMarkup("Los Angeles");
    b = FoscMarkup("May - August 2014");
    m = FoscMarkup.leftColumn([a, b]);
    m.show;

    img:: ![](../img/markup-markup-17.png)
    '''

    p = "%/fosc/docs/img/markup-markup-17".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *leftColumn { |markupList, direction|
        var contents, command;
        contents = [];
        markupList.do { |markup|
            contents = contents.add(FoscMarkup.prParseMarkupCommandArgument(markup));
        };
        command = FoscMarkupCommand('left-column', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *line

    LilyPond ``\line`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.line(#["Allegro", "assai"]);
    m.show;

    img:: ![](../img/markup-markup-18.png)
    '''

    p = "%/fosc/docs/img/markup-markup-18".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *line { |markupList, direction|
        var contents, command;
        contents = [];
        markupList = markupList.collect { |each| FoscMarkup(each) };
        markupList.do { |markup| contents = contents.addAll(markup.contents) };
        command = FoscMarkupCommand('line', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *makeImproperFractionMarkup

    Makes improper fraction markup.

    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    *makeImproperFractionMarkup { |rational, direction|
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *musicGlyph

    LilyPond ``\musicglyph`` markup command.

    Returns new markup.

    !!!TODO: update call to LilypondMusicGlyphs when files in 'ly' folder have been updated


    Example 1

    code::
    m = FoscMarkup.musicGlyph("accidentals.sharp");
    m.show;

    img:: ![](../img/markup-markup-19.png)
    '''

    p = "%/fosc/docs/img/markup-markup-19".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));





    Example 2

    LilypondMusicGlyphs.list.printAll;      // valid Lilypond glyph names
    m = FoscMarkup.musicGlyph("foo");       // throws error when glyphName is not valid
    '''
    -------------------------------------------------------------------------------------------------------- */
    *musicGlyph { |glyphName, direction|
        var glyphScheme, command;
        glyphName = glyphName.asString;
        if (LilypondMusicGlyphs.includes(glyphName).not) {
            throw("%:%: % is not a valid Lilypond glyph name."
                .format(this.species, thisMethod.name, glyphName));
        };
        glyphScheme = FoscScheme(glyphName).forceQuotes_(true);
        command = FoscMarkupCommand('musicglyph', glyphScheme);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *natural

    LilyPond ``\natural`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.natural;
    m.show;

    img:: ![](../img/markup-markup-20.png)
    '''

    p = "%/fosc/docs/img/markup-markup-20".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *natural { |direction|
        var command;
        command = FoscMarkupCommand('natural');
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *noteByNumber

    LilyPond ``\note-by-number`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.noteByNumber(log: 3, dotCount: 2, stemDirection: 1);
    m.show;

    img:: ![](../img/markup-markup-21.png)
    '''

    p = "%/fosc/docs/img/markup-markup-21".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *noteByNumber { |log, dotCount, stemDirection, direction|
        var command;
        command = FoscMarkupCommand('note-by-number', log, dotCount, stemDirection);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *null

    LilyPond ``\null`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.null;
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *null { |direction|
        var command;
        command = FoscMarkupCommand('null');
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *overlay

    !!!TODO: broken here and in abjad. Has \overlay been deprecated from lilypond?

    LilyPond ``\overlay`` markup command.

    Returns new markup.


    Example 1

    code::
    a = FoscMarkup("Los Angeles");
    b = FoscMarkup("May - August 2014");
    m = FoscMarkup.overlay([a, b]);
    m.show;

    img:: ![](../img/markup-markup-22.png)
    '''

    p = "%/fosc/docs/img/markup-markup-22".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *overlay { |markupList, direction|
        var contents, command;
        contents = [];
        markupList.do { |markup|
            contents = contents.add(FoscMarkup.prParseMarkupCommandArgument(markup));
        };
        command = FoscMarkupCommand('overlay', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *postscript

    LilyPond ``\postscript`` markup command.

    Returns new markup.


    Example 1

    code::
    p = FoscPostscript();
    p.moveto(1.0, 1.0);
    p.setlinewidth(2.5);
    p.lineto(3, -4);
    p.stroke;
    m = FoscMarkup.postscript(p);
    m.format;
    m.show;

    img:: ![](../img/markup-markup-23.png)
    '''

    p = "%/fosc/docs/img/markup-markup-23".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *postscript { |postscript, direction|
        var command;
        if (postscript.isKindOf(FoscPostscript)) { postscript = postscript.str };
        if (postscript.isString.not) {
            throw("%:% postscript argument must be a String or FoscPostscript."
                .format(this.species, thisMethod.name));
        };
        command = FoscMarkupCommand('postscript', postscript);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *rightColumn

    LilyPond ``\right-column`` markup command.

    Returns new markup.


    Example 1

    code::
    a = FoscMarkup("Los Angeles");
    b = FoscMarkup("May - August 2014");
    m = FoscMarkup.rightColumn([a, b]);
    m.show;

    img:: ![](../img/markup-markup-24.png)
    '''

    p = "%/fosc/docs/img/markup-markup-24".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *rightColumn { |markupList, direction|
    	var contents, command;
        contents = [];
        markupList.do { |markup|
            contents = contents.add(FoscMarkup.prParseMarkupCommandArgument(markup));
        };
        command = FoscMarkupCommand('right-column', contents);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *sharp

    LilyPond ``\sharp`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.sharp;
    m.show;

    img:: ![](../img/markup-markup-25.png)
    '''

    p = "%/fosc/docs/img/markup-markup-25".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *sharp { |direction|
        var command;
        command = FoscMarkupCommand('sharp');
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *triangle

    LilyPond ``\triangle`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.triangle(isFilled: true);
    m.show;

    img:: ![](../img/markup-markup-26.png)
    '''

    p = "%/fosc/docs/img/markup-markup-26".format(Platform.userExtensionDir);
    m.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *triangle { |direction, isFilled=true|
        var command;
        command = FoscMarkupCommand('triangle', isFilled.asBoolean);
        ^FoscMarkup(command, direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *vspace

    LilyPond ``\vspace`` markup command.

    Returns new markup.


    Example 1

    code::
    m = FoscMarkup.vspace(0.75);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *vspace { |amount, direction|
        var command;
        command = FoscMarkupCommand('vspace', amount);
        ^FoscMarkup(command, direction);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • bold

    LilyPond ``\bold`` markup command.

    Returns new markup.

    '''
    code::
    m = bold(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    bold {
        var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('bold', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • box

    LilyPond ``\box`` markup command.

    Returns new markup.

    '''
    code::
    m = box(FoscMarkup("Allegro assai", direction: 'up'));
    m = override(m, #['box-padding', 1]);
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    a[0].attach(m);
    a.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    box {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('box', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • bracket

    LilyPond ``\bracket`` markup command.

    Returns new markup.

    '''
    code::
    m = bracket(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    bracket {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('bracket', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • caps

    LilyPond ``\caps`` markup command.

    Returns new markup.

    '''
    code::
    m = caps(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    caps {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('caps', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • centerAlign

    LilyPond ``\center-align`` markup command.

    Returns new markup

    '''
    code::
    a = FoscMarkup("Allegro");
    b = centerAlign(FoscMarkup("non"));
    c = FoscMarkup("troppo");
    m = FoscMarkup.column([a, b, c]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    centerAlign {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('center-align', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • circle

    LilyPond ``\circle`` markup command.

    Returns new markup

    '''
    code::
    m = circle(FoscMarkup.fraction(3, 5));
    m = override(m, ['circle-padding', 0.5]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    circle {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('circle', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • dynamic

    LilyPond ``\dynamic`` markup command.

    Returns new markup.

    '''
    code::
    m = dynamic(FoscMarkup("sffz"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    dynamic {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('dynamic', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • finger

    LilyPond ``\finger`` markup command.

    Returns new markup

    '''
    code::
    m = finger(FoscMarkup(1));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    finger {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('finger', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • fontSize

    LilyPond ``\fontsize`` markup command.

    Returns new markup.

    '''
    code::
    m = fontSize(FoscMarkup("Allegro assai"), -3);
    m.show;
    '''

    '''
    code::
    m = fontSize(FoscMarkup("Allegro assai"), 10);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    fontSize { |fontSize|
    	var contents, command;
        fontSize = fontSize.asInteger;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('fontsize', fontSize, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • generalAlign

    LilyPond ``\general-align`` markup command.

    Returns new markup.

    '''
    code::
    m = FoscMarkup("Allegro assai");
    m = m.generalAlign('Y', 'UP');
    m.format;
    '''

    '''
    code::
    m = FoscMarkup("Allegro assai");
    m = m.generalAlign('Y', 0.75);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    generalAlign { |axis, direction|
    	var contents, command;
        axis = FoscScheme(axis);
        direction = FoscScheme(direction);
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('general-align', axis, direction, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • halign

    LilyPond halign markup command.

    Returns new markup.

    '''
    code::
    m = halign(FoscMarkup("Allegro assai"), 0);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    halign { |direction|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('halign', direction, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • hcenterIn

    LilyPond ``\hcenter-in`` markup command.

    Returns new markup.

    '''
    code::
    m = hcenterIn(FoscMarkup("Allegro assai"), 12);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    hcenterIn { |length|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('hcenter-in', length, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • huge

    LilyPond ``\huge`` markup command.

    Returns new markup.

    '''
    code::
    m = huge(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    huge {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('huge', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • italic

    LilyPond ``\italic`` markup command.

    Returns new markup.

    '''
    code::
    m = italic(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    italic {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('italic', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • larger

    LilyPond ``\larger`` markup command.

    Returns new markup.

    '''
    code::
    m = larger(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    larger {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('larger', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • override

    LilyPond ``\override`` markup command.

    Returns new markup.

    '''
    code::
    m = parenthesize(FoscMarkup("Allegro assai"));
    m = override(m, ['padding', 0.75]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    override { |newProperty|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        newProperty = FoscSchemePair(newProperty);
        command = FoscMarkupCommand('override', newProperty, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • padAround

    LilyPond ``\pad-around`` markup command.

    Returns new markup.

    '''
    code::
    m = padAround(FoscMarkup("Allegro assai"), 10);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    padAround { |padding|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('pad-around', padding, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • padMarkup

    LilyPond ``\pad-markup`` markup command.

    Returns new markup.

    '''
    code::
    m = padMarkup(FoscMarkup("Allegro assai"), 10);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    padMarkup { |padding|
        var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('pad-markup', padding, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • padToBox

    LilyPond pad-to-box markup command.

    '''
    code::
    m = padToBox(FoscMarkup("Allegro assai"), #[1,1], #[1,1]);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    padToBox { |xExtent, yExtent|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        xExtent = FoscSchemePair(xExtent);
        yExtent = FoscSchemePair(yExtent);
        command = FoscMarkupCommand('pad-to-box', xExtent, yExtent, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • parenthesize

    LilyPond ``\parenthesize`` markup command.

    Returns new markup.

    '''
    code::
    m = parenthesize(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    parenthesize {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('parenthesize', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • raise

    LilyPond ``\raise`` markup command.

    Returns new markup.

    '''
    code::
    m = raise(FoscMarkup("Allegro assai"), 0.35);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    raise { |amount|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('raise', amount, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • rotate

    LilyPond ``\rotate`` markup command.

    Returns new markup.

    '''
    code::
    m = rotate(FoscMarkup("Allegro assai"), 45);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    rotate { |angle|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('rotate', angle, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • sans

    LilyPond ``\sans`` markup command.

    Returns new markup.

    '''
    code::
    m = sans(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    sans {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('sans', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • scale

    LilyPond ``\scale`` markup command.

    Returns new markup.

    '''
    code::
    m = FoscMarkup("Allegro assai");
    m = m.scale(#[0.75, 1.5]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    scale { |factorPair|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        factorPair = FoscSchemePair(factorPair);
        command = FoscMarkupCommand('scale', factorPair, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • small

    LilyPond ``\small`` markup command.

    Returns new markup.

    '''
    code::
    m = small(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    small {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('small', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • smaller

    LilyPond ``\smaller`` markup command.

    Returns new markup.

    '''
    code::
    m = smaller(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    smaller {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('smaller', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • sub

    LilyPond ``\sub`` markup command.

    Returns new markup.

    '''
    code::
    a = [FoscMarkup("A"), sub(FoscMarkup("j"))];
    m = FoscMarkup.concat(a);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    sub {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('sub', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • super

    LilyPond ``\super`` markup command.

    Returns new markup.

    '''
    code::
    a = [FoscMarkup("1"), super(FoscMarkup("st"))];
    m = FoscMarkup.concat(a);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    super {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('super', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • tiny

    LilyPond ``\tiny`` markup command.

    Returns new markup.

    '''
    code::
    m = tiny(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    tiny {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('tiny', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • translate

    LilyPond translate markup command.

    Returns new markup.

    '''
    code::
    m = FoscMarkup("Allegro assai");
    m = m.translate(#[2, 2]);
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    translate { |offsetPair|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        offsetPair = FoscSchemePair(offsetPair);
        command = FoscMarkupCommand('translate', offsetPair, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • upright

    LilyPond ``\upright`` markup command.

    Returns new markup.

    '''
    code::
    m = upright(FoscMarkup("Allegro assai"));
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    upright {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('upright', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • vcenter

    LilyPond ``\vcenter`` markup command.

    Returns new markup.

    '''
    code::
    m = vcenter(FoscMarkup("Allegro assai"));
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    vcenter {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('vcenter', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • whiteout

    LilyPond ``\whiteout`` markup command.

    Returns new markup.

    '''
    code::
    m = whiteout(FoscMarkup("Allegro assai"));
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    whiteout {
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        command = FoscMarkupCommand('whiteout', contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • withColor

    LilyPond ``\with-color`` markup command.

    Returns new markup.

    '''
    code::
    m = withColor(FoscMarkup("Allegro assai"), 'blue');
    m.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    withColor { |color|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        color = FoscSchemeColor(color);
        command = FoscMarkupCommand('with-color', color, contents);
        ^this.species.new(command, this.direction);
    }
    /* --------------------------------------------------------------------------------------------------------
    • withDimensions

    '''
    code::
    m = FoscMarkup("Allegro assai");
    m = m.withDimensions(#[0, 20], #[0, -20]);
    m.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    withDimensions { |xExtent, yExtent|
    	var contents, command;
        contents = FoscMarkup.prParseMarkupCommandArgument(this);
        xExtent = FoscSchemePair(xExtent);
        yExtent = FoscSchemePair(yExtent);
        command = FoscMarkupCommand('with-dimensions', xExtent, yExtent, contents);
        ^this.species.new(command, this.direction);
    }
}
