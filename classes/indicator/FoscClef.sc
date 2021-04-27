/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscClef


SUMMARY:: Returns a FoscClef.


DESCRIPTION:: A clef


USAGE::

'''
Some available clefs.

FIXME: ERROR: Message '+' not understood.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69,71,72], [1/8]));
c = #['treble','alto','bass','treble^8','bass_8','tenor','bass^15','percussion'];
c.do { |name, i| a[i].attach(FoscClef(name)) };
a.show;
nointerpret
'''

'''
!!!TODO: tags not yet implemented

Clefs can be tagged.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
m = FoscClef('bass');
a[0].attach(m, tag: 'RED');
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscClef : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <clefNameToMiddleCPosition, <clefNameToStaffPositionZero, <toWidth;
    var <hide, <middleCPosition, <name;
    var <context='Staff', <formatSlot='opening', <parameter=true, <redraw=true;
    *initClass {
    	clefNameToMiddleCPosition = (
    		'treble': -6,
	        'alto': 0,
	        'tenor': 2,
	        'bass': 6,
	        'french': -8,
	        'soprano': -4,
	        'mezzosoprano': -2,
	        'baritone': 4,
	        'varbaritone': 4,
	        'percussion': 0,
        	'tab': 0
    	);

        toWidth = (
            'alto': 2.75,
            'bass': 2.75,
            'percussion': 2.5,
            'tenor': 2.75,
            'treble': 2.5
        );

    	clefNameToStaffPositionZero = (
            'treble': FoscPitch('B4'),
            'alto': FoscPitch('C4'),
            'tenor': FoscPitch('A3'),
            'bass': FoscPitch('D3'),
            'french': FoscPitch('D5'),
            'soprano': FoscPitch('G4'),
            'mezzosoprano': FoscPitch('E4'),
            'baritone': FoscPitch('F3'),
            'varbaritone': FoscPitch('F3'),
            'percussion': nil,
            'tab': nil,
		);
    }
    *new { |name='treble', hide=false|
    	if (name.isKindOf(FoscClef)) { name = name.name };
    	^super.new.init(name, hide);
    }
    init { |argName, argHide|
        assert(
            [Symbol, String].any { |type| argName.isKindOf(type) },
            "Can't initialize FoscClef from: %.".format(argName);
        );
    	name = argName.asString;
        hide = argHide;
    	middleCPosition = this.prCalculateMiddleCPosition(name);
        middleCPosition = FoscStaffPosition(middleCPosition);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context. 'Staff' by default.

    '''
    code::
    a = FoscClef('treble');
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • hide

    Is true when clef should not appear in output (but should still determine effective clef).

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    m = FoscClef('treble', hide: true);
    a[0].attach(m);
    a.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • middleCPosition

    !!!TODO: not yet implemented

    Gets middle C position of clef.

    '''
    code::
    a = FoscClef('treble');
    a.middleCPosition.postcs;
    '''
    '''
    code::
    a = FoscClef('alto');
    a.middleCPosition.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • name

    Gets name of clef.

    '''
    code::
    a = FoscClef('treble');
    a.name.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parameter

    Is true.

    '''
    code::
    a = FoscClef('treble');
    a.parameter.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • redraw

    Is true.

    '''
    code::
    a = FoscClef('treble');
    a.redraw.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Tweaks are not implemented on clef.

    The LilyPond '\clef' command refuses tweaks.

    Override the LilyPond 'Clef' grob instead.
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • format

    Formats clef.

    Returns string.

    '''
    code::
    a = FoscClef('treble');
    a.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    format {
    	^this.prGetLilypondFormat;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prCalculateMiddleCPosition
    -------------------------------------------------------------------------------------------------------- */
    prCalculateMiddleCPosition { |clefName|
        var alteration, baseName, suffix;
        alteration = 0;
        case
        { name.includes($_) } {
            # baseName, suffix = clefName.delimitBy($_);
            case
            { suffix == "8" } { alteration = 7 }
            { suffix == "15" } { alteration = 13 }
            {
                throw("%:%: bad clef alteration suffix: %.".format(this.species, thisMethod.name, suffix));
            }
        }
        { name.includes($^) } {
            # baseName, suffix = clefName.delimitBy($^);
            case
            { suffix == "8" } { alteration = -7 }
            { suffix == "15" } { alteration = -13 }
            {
                suffix.class.postln;
                throw("%:%: bad clef alteration suffix: %.".format(this.species, thisMethod.name, suffix));
            };
        }
        {
            baseName = clefName;
        };
        ^FoscClef.clefNameToMiddleCPosition[clefName.asSymbol] + alteration;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prClefNameToStaffPositionZero

    '''
    code::
    a = FoscClef('treble');
    a.prClefNameToStaffPositionZero('soprano').pitchName;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prClefNameToStaffPositionZero { |clefName|
    	^FoscClef.clefNameToStaffPositionZero[clefName];
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat

    '''
    code::
    a = FoscClef('treble');
    a.prGetLilypondFormat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^"\\clef %".format(name.quote);
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
    	var bundle;
        bundle = FoscLilypondFormatBundle();
        if (hide.not) {
            bundle.opening.commands.add(this.prGetLilypondFormat);
        };
        ^bundle;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *clefNames

    Array of all clef names.

    '''
    code::
    FoscClef.clefNames.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *clefNames {
    	var clefNames;
        clefNames = clefNameToMiddleCPosition.asSortedArray.collect { |each| each[0] };
        ^clefNames;
    }
    /* --------------------------------------------------------------------------------------------------------
    • *fromSelection

    !!!TODO
    -------------------------------------------------------------------------------------------------------- */
    *fromSelection {
    	^this.notYetImplemented(thisMethod);
    }
}
