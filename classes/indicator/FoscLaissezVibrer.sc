/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLaissezVibrer


SUMMARY:: Returns a FoscLaissezVibrer.


DESCRIPTION:: TODO


USAGE::

'''

• FoscLaissezVibrer (abjad 3.0)

Laissez vibrer.


• Example 1

code::
a = FoscChord(#[60,64,67,72], 1/4);
m = FoscLaissezVibrer();
a.attach(m);
a.show;

img:: ![](../img/indicator-laissez-vibrer-1.png)
'''

p = "%/fosc/docs/img/indicator-laissez-vibrer-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Laissez vibrer can be tweaked.

code::
a = FoscChord(#[60,64,67,72], 1/4);
m = FoscLaissezVibrer(tweaks: #[['color', 'blue']]);
a.attach(m);
a.show;

img:: ![](../img/indicator-laissez-vibrer-2.png)
'''

p = "%/fosc/docs/img/indicator-laissez-vibrer-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscLaissezVibrer : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <tweaks;
    var <formatSlot='right', <timeOrientation='right';
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
    '''
    • tweaks

    Gets tweaks.


    • Example 1

    code::
    m = FoscLaissezVibrer(tweaks: #[['color', 'blue']]);
    m.tweaks.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of laissez vibrer indicator.


    • Example 1

    code::
     m = FoscLaissezVibrer();
     m.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"\\laissezVibrer";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    
    def _get_lilypond_format(self):
        return str(self)
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
