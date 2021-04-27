/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscDynamicTrend


SUMMARY:: Returns a FoscDynamicTrend.


DESCRIPTION:: A dynamic trend.


USAGE::

'''
code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
'''

'''
Set 'leftBroken' to true to initialize without starting dynamic.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamicTrend('<', leftBroken: true));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
'''

'''
Crescendo dal niente.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('niente', hide: true));
a[0].attach(FoscDynamicTrend('o<'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
'''

'''
Decrescendo al niente.

FIXME: ERROR: Message 'name' not understood.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('f'));
a[0].attach(FoscDynamicTrend('>o'));
a[a.lastIndex].attach(FoscDynamic('niente', command: "\\!"));
a.show;
nointerpret
'''

'''
!!!TODO: abjad-flared-hairpin NOT IMPLEMENTED

Subito crescendo.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<|'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
'''

'''
!!!TODO: BROKEN. #constante-hairpin not implemented ??

Constante.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('--'));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
'''

'''
DynamicTrend can be tweaked.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0].attach(FoscDynamic('p'));
a[0].attach(FoscDynamicTrend('<', tweaks: #[['color', 'blue']]));
a[a.lastIndex].attach(FoscDynamic('f'));
a.show;
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
    • context

    Gets context. Returns 'Voice'.

    '''
    code::
    m = FoscDynamicTrend('<');
    m.context.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • leftBroken

    Is true when dynamic trend formats with left broken tag.

    '''
    code::
    m = FoscDynamicTrend('<', leftBroken: true);
    m.leftBroken.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • shape

    Gets shape. Returns string.

    '''
    code::
    m = FoscDynamicTrend('<');
    m.shape.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • spannerStart

    Is true.

    '''
    code::
    m = FoscDynamicTrend('<');
    m.spannerStart.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStart {
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    a = FoscDynamicTrend('<', tweaks: #[['color', 'blue']]);
    a.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *prConstanteHairpin
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
    • *prCircledTip
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
    • *prFlaredHairpin
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
    • *prTagHide

    !!!TODO
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
    • prGetLilypondFormat
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
    • prGetLilypondFormatBundle
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
