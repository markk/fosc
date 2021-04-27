/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscFermata


SUMMARY:: Returns a FoscFermata.


DESCRIPTION:: TODO


USAGE::

'''

• FoscFermata (abjad 3.0)

Fermata.


• Example 1

A short fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('shortfermata');
a[0][0].attach(m);
a.show;

img:: ![](../img/indicator-fermata-1.png)
'''

p = "%/fosc/docs/img/indicator-fermata-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

A regular fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata();
a[0][0].attach(m);
a.show;

img:: ![](../img/indicator-fermata-2.png)
'''

p = "%/fosc/docs/img/indicator-fermata-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

A long fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('longfermata');
a[0][0].attach(m);
a.show;

img:: ![](../img/indicator-fermata-3.png)
'''

p = "%/fosc/docs/img/indicator-fermata-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 4

A very long fermata.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('verylongfermata');
a[0][0].attach(m);
a.show;

img:: ![](../img/indicator-fermata-4.png)
'''

p = "%/fosc/docs/img/indicator-fermata-4".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 5

Fermata can be tweaked.

code::
a = FoscScore([FoscStaff([FoscNote(60, 1/4)])]);
m = FoscFermata('longfermata', tweaks: #[['color', 'blue']]);
a[0][0].attach(m);
a.show;

img:: ![](../img/indicator-fermata-5.png)
'''

p = "%/fosc/docs/img/indicator-fermata-5".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



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
    '''
    • command

    Gets command.


    • Example 1

    code::
    m = FoscFermata('longfermata');
    m.command;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context.


    • Example 1

    code::
    m = FoscFermata('longfermata');
    m.context;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Gets tweaks.


    • Example 1

    code::
    m = FoscFermata(tweaks: #[['color', 'blue']]);
    m.tweaks.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of breath mark.


    • Example 1

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
    '''
    • prGetLilypondFormat
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
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
