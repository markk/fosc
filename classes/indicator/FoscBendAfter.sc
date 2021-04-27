/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscBendAfter


SUMMARY:: Returns a FoscBendAfter.


DESCRIPTION:: A fall or doit.


USAGE::

• FoscBendAfter (abjad 3.0)

Fall or doit.


'''
A fall.

code::
a = FoscNote(60, 1/4);
m = FoscBendAfter(-4);
a.attach(m);
a.show;
'''

'''
A doit

code::
a = FoscNote(60, 1/4);
m = FoscBendAfter(2);
a.attach(m);
a.show;
'''

'''
Bend can be tweaked.

code::
a = FoscNote(60, 1/4);
m = FoscBendAfter(-4, tweaks: #[['color', 'blue']]);
a.attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscBendAfter : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <bendAmount, <tweaks;
    var <formatSlot='after', <timeOrientation='right';
    *new { |bendAmount= -4, tweaks|
        if (bendAmount.isKindOf(this)) { bendAmount = bendAmount.bendAmount };
        assert(bendAmount.isNumber);
        ^super.new.init(bendAmount, tweaks);
    }
    init { |argBendAmount, argTweaks|
        bendAmount = argBendAmount.asFloat;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • bendAmount

    Gets amount of bend after.

    '''
    code::
    a = FoscBendAfter(-4);
    a.bendAmount.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    m = FoscBendAfter(-4);
    tweak(m).color = 'blue';
    m.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of bend after.

    '''
    code::
    a = FoscBendAfter(-4);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"- \\bendAfter #'%".format(bendAmount);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks;
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions;
            bundle.after.articulations.addAll(localTweaks);
        };
        bundle.after.articulations.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
