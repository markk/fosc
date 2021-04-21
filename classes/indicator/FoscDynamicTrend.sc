/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscDynamicTrend


SUMMARY:: Returns a FoscDynamicTrend.


DESCRIPTION:: TODO


USAGE::

'''

• FoscDynamicTrend (abjad 3.0)

Dynamic trend.


• Example 1

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-1.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Set 'leftBroken' to true to initialize without starting dynamic.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamicTrend('<', leftBroken: true));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-2.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Crescendo dal niente.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('niente', hide: true));
a[0].attach(FoscDynamicTrend('o<'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-3.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 4

Decrescendo al niente.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('f'));
a[0].attach(FoscDynamicTrend('>o'));
a[a.lastIndex].attach(FoscDynamic('niente', command: "\\!"));
a.show;

img:: ![](../img/indicator-dynamic-trend-4.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-4".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 5 !!!TODO: abjad-flared-hairpin NOT IMPLEMENTED

Subito crescendo.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<|'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-5.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-5".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 6 !!!TODO: BROKEN. #constante-hairpin not implemented ??

Constante.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('--'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-6.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-6".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 7

DynamicTrend can be tweaked.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<', tweaks: #[['color', 'blue']]));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;

img:: ![](../img/indicator-dynamic-trend-7.png)
'''

p = "%/fosc/docs/img/indicator-dynamic-trend-7".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscDynamicTrend : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <knownShapes, <crescendoStart="\\<", <decrescendoStart="\\>";
    var <shape, <leftBroken, <tweaks;
    var <context='Voice', <formatSlot='after';
    *initClass {
        knownShapes = #["<", "o<", "<|", "o<|", ">", ">o", "|>", "|>o", "--"];
    }
    *new { |shape="<", leftBroken=false, tweaks|
        //assert(shape.isString);
        shape = shape.asString;
        assert(
            knownShapes.includesEqual(shape),
            "FoscDynamicTrend: not a valid shape: %.".format(shape)
        );
        ^super.new.init(shape, leftBroken, tweaks);
    }
    init { |argShape, argLeftBroken, argTweaks|
        shape = argShape;
        leftBroken = argLeftBroken;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context. Returns 'Voice'.


    • Example 1

    code::
    m = FoscDynamicTrend('<');
    m.context.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • leftBroken

    Is true when dynamic trend formats with left broken tag.


    • Example 1

    code::
    m = FoscDynamicTrend('<', leftBroken: true);
    m.leftBroken;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • shape

    Gets shape. Returns string.


    • Example 1

    code::
    m = FoscDynamicTrend('<');
    m.shape.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • spannerStart
    
    Is true.


    • Example 1

    code::
    m = FoscDynamicTrend('<');
    m.spannerStart;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStart {
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Gets tweaks.


    • Example 1

    code::
    a = FoscDynamicTrend('<', tweaks: #[['color', 'blue']]);
    a.tweaks.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prConstanteHairpin
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prConstanteHairpin {
        ^FoscLilypondGrobOverride(
            grobName: "Hairpin",
            isOnce: true,
            propertyPath: "stencil",
            value: "#constante-hairpin"
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prCircledTip
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prCircledTip {
        ^FoscLilypondGrobOverride(
            grobName: "Hairpin",
            isOnce: true,
            propertyPath: "circled-tip",
            value: true
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prFlaredHairpin
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prFlaredHairpin {
        ^FoscLilypondGrobOverride(
            grobName: "Hairpin",
            isOnce: true,
            propertyPath: "stencil",
            value: "#abjad-flared-hairpin"
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prTagHide

    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prTagHide { |strings|
        ^this.notYetImplemented(thisMethod);
        // var abjadTags;
        // abjadTags = FoscTags();
        // ^FoscLilypondFormatManager.tag(
        //     strings,
        //     deactivate: false,
        //     tag: 'HIDE_TO_JOIN_BROKEN_SPANNERS'
        // );
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        var strings, override, string;
        strings = [];
        if (shape.contains("--")) {
            override = FoscDynamicTrend.prConstanteHairpin;
            string = override.tweakString;
            strings = strings.add(string);
        };
        if (shape.contains("o")) {
            override = FoscDynamicTrend.prCircledTip;
            string = override.tweakString;
            strings = strings.add(string);
        }; 
        if (shape.contains("|")) {
            override = FoscDynamicTrend.prFlaredHairpin;
            string = override.tweakString;
            strings = strings.add(string);
        }; 
        case
        { shape.contains("<") || shape.contains("--") } {
            strings = strings.add(FoscDynamicTrend.crescendoStart);
        }
        { shape.contains(">") } {
            strings = strings.add(FoscDynamicTrend.decrescendoStart);
        } {
            throw("%:%: bad value for shape: %.".format(this.species, thisMethod.name, shape));
        };
        //!!!TODO: if (leftBroken) { strings = FoscDynamicTrend.prTagHide(string) }; 
        ^strings;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
        var bundle, localTweaks, strings;
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions;
            bundle.after.spanners.addAll(localTweaks);
        };
        strings = this.prGetLilypondFormat;
        bundle.after.spanners.addAll(strings);
        ^bundle;
    }
}
