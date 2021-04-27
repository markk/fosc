/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscArticulation


SUMMARY:: Returns a FoscArticulation.


DESCRIPTION:: Create articulations.


USAGE::

• FoscArticulation (abjad 3.0)

Articulation.


'''
Initialize articulation from name.

code::
a = FoscNote(60, 1/4);
m = FoscArticulation('staccato');
a.attach(m);
a.show;
'''
'''
Initialize articulation from abbreviation.

code::
a = FoscNote(60, 1/4);
m = FoscArticulation('.');
a.attach(m);
a.show;
'''
'''
Initialize articulation with direction.

code::
a = FoscNote(60, 1/4);
m = FoscArticulation('.', direction: 'up');
a.attach(m);
a.show;
'''
'''
Articulations can be tweaked.

code::
a = FoscNote(60, 1/4);
m = FoscArticulation('marcato');
tweak(m).color = 'blue';
tweak(m).yOffset = -10;
a.attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscArticulation : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar articulationsSupported, <shortcutToWord;
    var <name, <direction, <tweaks, <formatSlot;
    *initClass {
        articulationsSupported = #[
            'accent',
            'marcato',
            'staccatissimo',
            'espressivo',
            'staccato',
            'tenuto',
            'portato',
            'upbow',
            'downbow',
            'flageolet',
            'thumb',
            'lheel',
            'rheel',
            'ltoe',
            'rtoe',
            'open',
            'stopped',
            'turn',
            'reverseturn',
            'trill',
            'prall',
            'mordent',
            'prallprall',
            'prallmordent',
            'upprall',
            'downprall',
            'upmordent',
            'downmordent',
            'pralldown',
            'prallup',
            'lineprall',
            'signumcongruentiae',
            'shortfermata',
            'fermata',
            'longfermata',
            'verylongfermata',
            'segno',
            'coda',
            'varcoda',
            '^',
            '+',
            '-',
            '|',
            '>',
            '.',
            '_'
        ];

        shortcutToWord = (
            '^': 'marcato',
            '+': 'stopped',
            '-': 'tenuto',
            '|': 'staccatissimo',
            '>': 'accent',
            '.': 'staccato',
            '_': 'portato'
        );
    }
    *new { |name, direction, tweaks|
        var directions;
        if (name.isKindOf(this)) {
            name = name.name;
            direction = direction ?? { name.direction };
        };
        name = name.asSymbol;
        direction = direction.asSymbol.toTridirectionalLilypondString;
        directions = #['up', 'down', 'center', nil];
        if (directions.includes(direction).not) {
            throw("%::new % is not a valid argument for direction.".format(this.species, direction));
        };
        //!!!TODO:
        // direction_ = String.to_tridirectional_ordinal_constant(direction)
        // if direction_ is not None:
        //     assert isinstance(direction_, enums.VerticalAlignment), repr(direction_)
        //     assert direction_ in (enums.Up, enums.Down, enums.Center), repr(direction_)
        ^super.new.init(name, direction, tweaks);
    }
    init { |argName, argDirection, argTweaks|
        name = argName;
        direction = argDirection;
        formatSlot = 'after';
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • format

    Formats articulation.
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    • illustrate

    Illustrates articulation.

    Returns LilyPond file.
    -------------------------------------------------------------------------------------------------------- */
    illustrate {
        var note, articulation, lilypondFile;
        note = FoscNote(60, 1/4);
        articulation = this.copy;
        note.attach(articulation);
        lilypondFile = FoscLilypondFile(note);
        ^lilypondFile;
    }
    /* --------------------------------------------------------------------------------------------------------
    • str (abjad: __str__)

    Gets string representation of articulation.

    '''
    code::
    m = FoscArticulation('accent', direction: 'above');
    m.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        var string;
        if (name.notNil) {
            string = FoscArticulation.shortcutToWord[name];
            if (string.isNil) { string = name };
            if (direction.isNil) {
                direction = "-";
            } {
                direction = direction.toTridirectionalLilypondSymbol(direction);
            };
            ^(direction ++ "\\" ++ string);
        };
        ^"";
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
    code::
    • prGetLilypondFormatBundle;
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • direction

    Gets direction of articulation.

    '''
    Without direction.

    code::
    m = FoscArticulation('.');
    m.direction.isNil.postln;
    '''
    '''
    With direction.

    code::
    m = FoscArticulation('.', direction: 'up');
    m.direction.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • name

    Gets name of articulation.

    '''
    code::
    m = FoscArticulation('staccato');
    m.name.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    m = FoscArticulation('marcato');
    tweak(m).color = 'blue';
    tweak(m).yOffset = -10;
    m.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
}
