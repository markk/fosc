/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscBreathMark


SUMMARY:: Returns a FoscBreathMark.


DESCRIPTION:: A breath mark.


USAGE::

'''
Attached to a single note.

code::
a = FoscNote(60, 1/4);
m = FoscBreathMark();
a.attach(m);
a.show;
'''

'''
Attached to notes in a staff.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69,71,72], [1/8]));
a[3].attach(FoscBreathMark());
a[7].attach(FoscBreathMark());
a.show;
'''

'''
Breath mark can be tweaked.

code::
a = FoscNote(60, 1/4);
m = FoscBreathMark(tweaks: #[['color', 'blue']]);
a.attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscBreathMark : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <tweaks;
    var <formatSlot='after', <timeOrientation='right';
    *new { |tweaks|
        ^super.new.init(tweaks);
    }
    init { |argTweaks|
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.


    '''
    code::
    m = FoscBreathMark(tweaks: #[['color', 'blue']]);
    m.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of breath mark.
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"\\breathe";
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
            localTweaks = tweaks.prListFormatContributions(directed: false);
            bundle.after.commands.addAll(localTweaks);
        };
        bundle.after.commands.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
