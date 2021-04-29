/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscPitch


SUMMARY:: Returns a FoscPitch.


DESCRIPTION:: TODO


USAGE::

'''

• FoscPitch


### TODO: abjad public methods
accidental_spelling	// Accidental spelling of Abjad session (retrieves from abjad_configuration)
apply_accidental

### TODO: make set operations work for collections of pitches, eg:
code::
[60, 61, 62].collect { |each| FoscPitch(each) }.sect([60, 62].collect { |each| FoscPitch(each) });


• Example 1

Quantize to 1/8th tones

code::
a = FoscPitch(60.25);
a.pitchNumber;
a.str;

code::
a = FoscPitch(60.75);
a.pitchNumber;
a.str;


• Example 2

code::
a = FoscPitch("C4", arrow: 'up');
a.pitchNumber;
a.str;

code::
a = FoscPitch("C#4", arrow: 'down');
a.pitchNumber;
a.str;
'''
------------------------------------------------------------------------------------------------------------ */
FoscPitch : FoscObject {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	var <pitchClass, <octave, <accidental, <arrow;
	var manager;
	*new { |val, arrow|
		^super.new.init(val, arrow);
	}
	init { |val, argArrow|
		var initializer, arrowState;
		manager = FoscPitchNameManager;

        initializer = case
		{ val.isNumber } {
            FoscNumberedPitch(val);
        }
		{ val.isKindOf(FoscPitch) } {
            FoscNamedPitch(val.pitchName, val.arrow);
        }
		{ val.asString.isPitchName } {
            FoscNamedPitch(val, argArrow);
        }
		{ val.asString.isLilyPondPitchName } {
            FoscNamedPitch(manager.lilypondPitchNameToPitchName(val), argArrow);
        }
		{ val.isKindOf(FoscPitchClass) } {
            FoscPitch(val.pitchClassName ++ "4", val.arrow);
        }
		{
            throw("Can't initialize % from value: %".format(this.species, val));
        };

		pitchClass = initializer.pitchClass;
		octave = initializer.octave;
		accidental = initializer.accidental;
        arrow = initializer.arrow;
        if (arrow.notNil && { #['up', 'down'].includes(arrow) }) {
            accidental.arrow_(arrow);
        };
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS: SPECIAL
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • == (abjad: __eq__)

    code::
    a = FoscPitch('C#4');
    b = FoscPitch('Db4');
    c = FoscPitch('B3');
    a == a;
    a == b; // Enharmonic equivalents are treated as equal
    code::
    a == c;
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        ^(this.pitchNumber == FoscPitch(expr).pitchNumber);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • !=

    code::
    a = FoscPitch('C#4');
    b = FoscPitch('Db4');
    c = FoscPitch('B3');
    a != b; // Enharmonic equivalents are treated as equal
    code::
    a != c;
    a != a;
    '''
    -------------------------------------------------------------------------------------------------------- */
    != { |expr|
        ^(this == expr).not;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • >

    Is true when arg can be coerced to a pitch and when this pitch is greater than arg. Otherwise false.

    Returns true or false.


    code::
    a = FoscPitch('C#4');
    b = FoscPitch('D#4');
    c = FoscPitch('B3');
    a > b;
    a > c;
    a > a;
    '''
    -------------------------------------------------------------------------------------------------------- */
    > { |expr|
        ^(this.pitchNumber > FoscPitch(expr).pitchNumber);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • >=

    Is true when arg can be coerced to a pitch and when this pitch is greater than or equal to arg. Otherwise false.

    Returns true or false.

    code::
    a = FoscPitch('C#4');
    b = FoscPitch('D#4');
    c = FoscPitch('B3');
    a >= a;
    a >= b;
    a >= c;
    '''
    -------------------------------------------------------------------------------------------------------- */
    >= { |expr|
        ^(this.pitchNumber >= FoscPitch(expr).pitchNumber);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • <

    Is true when arg can be coerced to a pitch and when this pitch is less than arg. Otherwise false.

    Returns true or false.

    code::
    a = FoscPitch('C#4');
    b = FoscPitch('D#4');
    c = FoscPitch('B3');
    a < b;
    a < c;
    a < a;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • <=

    Is true when arg can be coerced to a pitch and when this pitch is less than or equal to arg. Otherwise false.

    Returns true or false.

    code::
    a = FoscPitch('C#4');
    b = FoscPitch('D#4');
    c = FoscPitch('B3');
    a <= a;
    a <= b;
    a <= c;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
 '''
	• add (abjad: __add__)

	Adds arg to numberd pitch.

	Returns new numbered pitch.


 code::
	x = FoscPitch('C#4');
	x = x + 2;
	x.pitchName;

 code::
    x = FoscPitch('C#4') + FoscInterval(2);
    x.pitchName;
 '''
	-------------------------------------------------------------------------------------------------------- */
    add { |expr|
        if (expr.isKindOf(FoscInterval)) { expr = expr.number };
    	^FoscPitch(this.pitchNumber + FoscPitch(expr).pitchNumber);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"FoscPitch('%')".format(this.pitchName);
    }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • asFloat (abjad: __float__)

	Changes numbered pitch to float.

	Returns float.


    code::
	x = FoscPitch('C+4');
    x.asFloat;
    '''
	-------------------------------------------------------------------------------------------------------- */
    asFloat {
    	^this.pitchNumber.asFloat;
    }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • asInteger (abjad: __int__)

	Changes numbered pitch to integer.

	Returns integer.


    code::
	x = FoscPitch('C+4');
    x.asInt;
    '''
	-------------------------------------------------------------------------------------------------------- */
    asInteger {
    	^this.pitchNumber.asInteger;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • neg

	Negates numbered pitch.

	Returns new numbered pitch.


    code::
	a = FoscPitch('C#4');
	b = a.neg;
	b.pitchNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	neg {
		^FoscPitch(this.pitchNumber.neg);
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • storeArgs
    '''
    -------------------------------------------------------------------------------------------------------- */
    storeArgs {
        ^[this.pitchName];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str (abjad: __str__)

    code::
    a = FoscPitch('C#4');
    a.str;

    •••••••••••••••••••• TODO
    code::
    a = FoscPitch('C#4', arrow: 'up');
    a.str;
    '''
	-------------------------------------------------------------------------------------------------------- */
    str {
    	^this.lilypondPitchName;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • pitchString

    code::
    a = FoscPitch('C#4');
    a.pitchString;
    a.ps;
    '''
    -------------------------------------------------------------------------------------------------------- */
    pitchString {
        ^"\"%\"".format(this.pitchName);
    }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • sub
    '''
    -------------------------------------------------------------------------------------------------------- */
    sub { |expr|
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE PROPERTIES
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • accidentalName

    code::
    FoscPitch('Db5').accidentalName;

    •••••••••••••••••••• DONE
    code::
    FoscPitch('Db5', arrow: 'up').accidentalName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	accidentalName {
		^accidental.name;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • alterationInSemitones

    code::
    FoscPitch('Db5').alterationInSemitones;

    •••••••••••••••••••• DONE
    code::
    FoscPitch('Db5', arrow: 'down').alterationInSemitones;
    '''
	-------------------------------------------------------------------------------------------------------- */
	alterationInSemitones {
		^accidental.semitones;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • diatonicPitchClassName

    code::
    FoscPitch('Db5').diatonicPitchClassName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	diatonicPitchClassName {
		^pitchClass.diatonicPitchClassName;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • diatonicPitchClassNumber

    code::
    FoscPitch('Db5').diatonicPitchClassNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	diatonicPitchClassNumber {
		^pitchClass.diatonicPitchClassNumber;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • diatonicPitchName

    code::
    FoscPitch('Db5').diatonicPitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	diatonicPitchName {
		^(pitchClass.diatonicPitchClassName ++ octave.octaveName);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • diatonicPitchNumber

    code::
    FoscPitch('Db5').diatonicPitchNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	diatonicPitchNumber {
		^((12 * (octave.octaveNumber + 1)) + pitchClass.diatonicPitchClassNumber);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • isDiatonic

    code::
    FoscPitch('Db5').isDiatonic;
    '''
	-------------------------------------------------------------------------------------------------------- */
	isDiatonic {
		^(this.pitchClassName == this.diatonicPitchClassName);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • isFlattened

    code::
    FoscPitch('Db5').isFlattened;
    '''
	-------------------------------------------------------------------------------------------------------- */
	isFlattened {
		^#['b', 'bb', '~', 'b~'].includes(this.accidentalName.asSymbol);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • isSharpened

    code::
    FoscPitch('Db5').isSharpened;
    '''
	-------------------------------------------------------------------------------------------------------- */
	isSharpened {
		^#['#', '##', '+', '#+'].includes(this.accidentalName.asSymbol);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • lilypondPitchName

    code::
    FoscPitch('Db5').lilypondPitchName;

    •••••••••••••••••••• DONE
    code::
    FoscPitch('Db5', arrow: 'up').lilypondPitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	lilypondPitchName {
		^manager.pitchNameToLilypondPitchName(this.pitchName, arrow: arrow);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • midicps

    code::
    FoscPitch('A4').midicps;
    '''
	-------------------------------------------------------------------------------------------------------- */
	midicps {
		^this.pitchNumber.midicps;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • octaveName

    code::
    FoscPitch('Db5').octaveName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	octaveName {
		^octave.octaveName;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • octaveNumber

    code::
    FoscPitch('Db5').octaveNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	octaveNumber {
		^octave.octaveNumber;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • pitchClassName

    code::
    FoscPitch('Db5').pitchClassName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	pitchClassName {
		^pitchClass.pitchClassName;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • pitchClassNumber

    code::
    FoscPitch('Db5').pitchClassNumber;

    •••••••••••••••••••• TODO
    code::
    FoscPitch('Db5', arrow: 'up').pitchClassNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	pitchClassNumber {
		^pitchClass.pitchClassNumber;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • pitchName

    code::
    FoscPitch('Db5').pitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	pitchName {
		^(pitchClass.pitchClassName ++ octave.octaveName);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • pitchNumber

    code::
    FoscPitch('Db~5').pitchNumber;

    code::
    FoscPitch(61.5).pitchNumber;

    code::
    FoscPitch('C4', arrow: 'up').pitchNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	pitchNumber {
        var result;
		result = (pitchClass.pitchClassNumber + 12 + (octave.octaveNumber * 12));
        if (arrow.notNil) {
            switch(arrow,
                'up', { result = result + 0.25 },
                'down', { result = result - 0.25 },
            );
        };
        ^result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS: TRANSFORMATIONS
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • applyAccidental
    '''
	-------------------------------------------------------------------------------------------------------- */
	applyAccidental { |accidental|
		^this.notYetImplemented(thisMethod);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • multiply

    Multiplies pitch-class of numbered pitch by n and maintains octave.

	Returns new numbered pitch.


    code::
    a = FoscPitch(62);
    a = a.multiply(3);
    a.pitchNumber;
    '''
	-------------------------------------------------------------------------------------------------------- */
	multiply { |n=1|
		var pitchClassNumber, octaveFloor;
		pitchClassNumber = (this.pitchClassNumber * n) % 12;
		octaveFloor = (this.octaveNumber + 1) * 12;
		^this.species.new(pitchClassNumber + octaveFloor);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • invert

    code::
	x = FoscPitch('Eb4');
	x = x.invert(axis: 'D4');
	x.pitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	invert { |axis|
		axis = FoscPitch(axis);
		axis = axis.transpose(axis.pitchNumber - this.pitchNumber);
		^this.species.new(axis);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • respellWithFlats

    code::
    x = FoscPitch('C#4');
	x.pitchName;
	x = x.respellWithFlats;
	x.pitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	respellWithFlats {
		^this.species.new(manager.pitchClassNumberToPitchClassNameWithFlats(pitchClass.pitchClassNumber) ++ octave.octaveName);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • respellWithSharps

    code::
    x = FoscPitch('Db4');
	x.pitchName;
	x = x.respellWithSharps;
	x.pitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	respellWithSharps {
		^this.species.new(manager.pitchClassNumberToPitchClassNameWithSharps(pitchClass.pitchClassNumber) ++ octave.octaveName);
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • toStaffPosition

    Changes named pitch to staff position.
    '''
    -------------------------------------------------------------------------------------------------------- */
	toStaffPosition { |clef|
        var staffPositionNumber, staffPosition;
        staffPositionNumber = this.diatonicPitchNumber;
        if (clef.notNil) {
            clef = FoscClef(clef);
            staffPositionNumber = staffPositionNumber + clef.middleCPositionNumber;
        };
        staffPosition = FoscStaffPosition(staffPositionNumber);
        ^staffPosition;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • transpose

    code::
    x = FoscPitch('A4');
	x = x.transpose(semitones: 6);
	x.pitchName;
    '''
	-------------------------------------------------------------------------------------------------------- */
	transpose { |semitones|
		var respell, result;
		respell = case
		{ this.isSharpened } { \respellWithSharps }
		{ this.isFlattened } { \respellWithFlats };
		result = this.species.new(this.pitchNumber + semitones);
		if (respell.notNil && { result.isDiatonic.not }) { result = result.perform(respell) };
		^result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS: DISPLAY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • show
    '''
	-------------------------------------------------------------------------------------------------------- */
	show {
		^this.notYetImplemented;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • play
    '''
	-------------------------------------------------------------------------------------------------------- */
	play {
		^this.notYetImplemented;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • inspect

    code::
    FoscPitch("C#5").inspect;
    '''
	-------------------------------------------------------------------------------------------------------- */
	inspect {
		super.inspect(#['pitchName', 'pitchNumber']);
	}
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetFormatSpecification

    - abjad 2.21
    def _get_format_specification(self):
    import abjad
    return abjad.FormatSpecification(
        self,
        coerce_for_equality=True,
        repr_is_indented=False,
        storage_format_args_values=[self.name],
        storage_format_is_indented=False,
        storage_format_kwargs_names=['arrow'],
        )
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetFormatSpecification {
        ^FoscFormatSpecification(
            coerceForEquality: true,
            reprIsIndented: false,
            storageFormatArgsValues: [this.name],
            storageFormatIsIndented: false,
            storageFormatKwargsNames: #['arrow']
        );
    }
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
    • prListFormatContributions

    - abjad 2.2.1
    def _list_format_contributions(self):
        contributions = []
        if self.arrow is None:
            return contributions
        override_string = r'\once \override Accidental.stencil ='
        override_string += ' #ly:text-interface::print'
        contributions.append(override_string)
        string = 'accidentals.{}.arrow{}'
        string = string.format(self.accidental.name, str(self.arrow).lower())
        override_string = r'\once \override Accidental.text ='
        override_string += r' \markup {{ \musicglyph #"{}" }}'
        override_string = override_string.format(string)
        contributions.append(override_string)
        return contributions

    code::
    a = FoscStaff([FoscNote(FoscPitch("C4", arrow: 'down'), [1, 4])]);
    a.show;

    img:: ![](../img/pitch-pitch-1.png)
    '''

    p = "%/fosc/docs/img/pitch-pitch-1".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a = FoscStaff([FoscNote(FoscPitch("C#4", arrow: 'up'), [1, 4])]);
    a.show;

    img:: ![](../img/pitch-pitch-2.png)
    '''

    p = "%/fosc/docs/img/pitch-pitch-2".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    prListFormatContributions {
        var contributions, overrideString, string;
        contributions = [];
        if (this.arrow.isNil) { ^contributions };
        overrideString = "\\once \\override Accidental.stencil =";
        overrideString = overrideString + "#ly:text-interface::print";
        contributions = contributions.add(overrideString);
        string = "accidentals.%.arrow%";
        // to be changed to: this.accidental.name (also change in FoscAccidental, FoscNoteHead)
        string = string.format(this.accidental.unabbreviatedName, this.arrow.asString.toLower);
        overrideString = "\\once \\override Accidental.text =";
        overrideString = overrideString + "\\markup { \\musicglyph #\"%\" }";
        overrideString = overrideString.format(string);
        contributions = contributions.add(overrideString);
        ^contributions;
    }
}
/* ------------------------------------------------------------------------------------------------------------
Initializer
'''
• FoscPitchInitializer
'''
------------------------------------------------------------------------------------------------------------ */
FoscPitchInitializer {
	var <pitchClass, <accidental, <octave, <arrow;
}
/* ------------------------------------------------------------------------------------------------------------
'''
• FoscNamedPitch

code::
a = FoscPitch("C4", arrow: 'up');
a.pitchNumber;
a.str;
'''
------------------------------------------------------------------------------------------------------------ */
FoscNamedPitch : FoscPitchInitializer {
	var <pitchName;
	*new { |pitchName, arrow|
		^if (pitchName.asString.isPitchName ){
            super.new.init(pitchName.asString, arrow);
        } {
            throw("Can not initialize % from value: %".format(this.species, pitchName));
        };
	}
	init { |argPitchName, argArrow|
		pitchName = argPitchName;
		pitchClass = FoscPitchClass(pitchName);
		accidental = pitchClass.accidental;
		octave = FoscOctave(pitchName);
        arrow = argArrow;
	}
}
/* ------------------------------------------------------------------------------------------------------------
'''
• FoscNumberedPitch


code::
a = FoscPitch(60.25);
a.str;
a.pitchNumber;

code::
a = FoscPitch(60.75);
a.str;
a.pitchNumber;
'''
------------------------------------------------------------------------------------------------------------ */
FoscNumberedPitch : FoscPitchInitializer {
    var <pitchNumber;
	*new { |pitchNumber|
		^if (pitchNumber.isNumber) {
			super.new.init(pitchNumber);
		} {
			throw("Can not initialize % from value: %".format(this.species, pitchNumber));
		};
	}
	init { |argPitchNumber|
        var list, index;

		pitchNumber = argPitchNumber;
		pitchClass = FoscPitchClass(pitchNumber);
		accidental = pitchClass.accidental;
		octave = FoscOctave(((pitchNumber / 12) - 1).floor);

        list = #[0, 0.25, 0.5, 0.75];
        index = list.indexOf(pitchNumber.frac.nearestInList(list));
        arrow = switch(index, 1, 'up', 3, 'down', nil);
	}
}
