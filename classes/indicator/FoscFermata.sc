/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscFermata


SUMMARY:: Returns a FoscFermata.


DESCRIPTION:: A fermata.


USAGE::

'''
A short fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('shortfermata');
a[0][0].attach(m);
a.show;
'''

'''
A regular fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata();
a[0][0].attach(m);
a.show;
'''

'''
A long fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('longfermata');
a[0][0].attach(m);
a.show;
'''

'''
A very long fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('verylongfermata');
a[0][0].attach(m);
a.show;
'''

'''
Fermata can be tweaked.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('longfermata', tweaks: #[['color', 'blue']]);
a[0][0].attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscFermata : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <allowableCommands;
    var <command, <tweaks;
    var <context='Score', <formatSlot='after';
    *initClass {
        allowableCommands = #['fermata', 'longfermata', 'shortfermata', 'verylongfermata'];
    }
    *new { |command='fermata', tweaks|
        assert(
            allowableCommands.includes(command.asSymbol),
            "FoscFermata:new: invalid command argument: '%'. Valid commands are: %."
                .format(command, "".ccatList(allowableCommands)[1..]);
        );
        ^super.new.init(command, tweaks);
    }
    init { |argCommand, argTweaks|
        command = argCommand;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • command

    Gets command.

    '''
    code::
    m = FoscFermata('longfermata');
    m.command.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context.

    '''
    code::
    m = FoscFermata('longfermata');
    m.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    m = FoscFermata(tweaks: #[['color', 'blue']]);
    m.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of breath mark.

    '''
    code::
    m = FoscFermata('longfermata');
    m.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^("\\" ++ command);
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
