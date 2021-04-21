/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscPitchSegment


SUMMARY:: Returns a FoscPitchSegment.


DESCRIPTION:: TODO


USAGE::

'''

• FoscPitchSegment

• test 1
code::
n = [60, 62, 64];
FoscPitchSegment(n).cs;

• test 2
code::
n = "Bb4 F#5 C4 Cb4 E+4 G4 D+5";
FoscPitchSegment(n).cs;

• test 3: FoscPitchSegment does not wrap FoscPitchSegments
code::
n = "Bb4 F#5 <C4 Cb4> E+4 G4 D+5";
FoscPitchSegment(n).cs;
'''
------------------------------------------------------------------------------------------------------------ */
FoscPitchSegment : FoscTypedList {
    var <player;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	*new { |items|
        items = FoscPitchParser(items);
		^super.new(items, FoscPitch);
	}
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString
    
    code::
    FoscPitchSegment([60, 62, 64]).cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // asCompileString {
    //     var pitchesStr;
    //     pitchesStr = this.pitches.collect { |each| each.cs }.join(",\n\t");
    //     ^"%([\n\t%\n])".format(this.species, pitchesStr);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • ==
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        var exprItems;
        if (expr.isKindOf(this.species).not) { ^false };
        if (this.size != expr.size) { ^false };
        exprItems = expr.items;
        this.items.do { |each, i| if (each != exprItems[i]) { ^false } };
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • !=
    '''
    -------------------------------------------------------------------------------------------------------- */
    != { |expr|
        ^(this == expr).not
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • storeArgs
    '''
    -------------------------------------------------------------------------------------------------------- */
    storeArgs {
        ^[this.pitches]
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    code::
    FoscPitchSegment([60, 62, 64]).str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"<%>".format("".scatList(this.pitches.collect { |each| each.str })[1..]);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
    '''
    • pitches
    '''
    -------------------------------------------------------------------------------------------------------- */
    pitches {
        ^this.items;
    }
    /* --------------------------------------------------------------------------------------------------------
 '''
	• pitchNumbers
 '''
	-------------------------------------------------------------------------------------------------------- */
	pitchNumbers {
		^this.items.collect { |each| each.pitchNumber };
	}
	/* --------------------------------------------------------------------------------------------------------
 '''
	• pitchNames
 '''
	-------------------------------------------------------------------------------------------------------- */
	pitchNames {
		^this.items.collect { |each| each.pitchName };
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • pitchString
    '''
    -------------------------------------------------------------------------------------------------------- */
	pitchString {
        var pitchNames;
        pitchNames = this.pitchNames.collect { |each| each.asString };  
        ^pitchNames.join(" ").asCompileString;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • invert
	'''Inverts pitch segment about `axis`.

	Returns new pitch segment.
	'''

    code::
	a = FoscPitchSegment([60, 61, 62, 63]);
	b = a.invert(66);
	b.pitchNumbers;

    code::
	a = FoscPitchSegment([60, 61, 62, 63]);
	b = a.invert;
	b.pitchNumbers;
    '''
	-------------------------------------------------------------------------------------------------------- */
	invert { |axis|
		axis = axis ?? { this.items[0] };
		^this.species.new(this.items.collect { |each| each.invert(axis) });
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • isEquivalentUnderTransposition

	'''True if pitch set is equivalent to `expr` under transposition.
	Otherwise false.

	Returns true or false.
	'''

    code::
	a = FoscPitchSegment([60, 61, 62]);
    b = FoscPitchSegment([61, 62, 63]);
    c = FoscPitchSegment([60, 61, 63]);
    isEquivalentUnderTransposition(a, b);
    isEquivalentUnderTransposition(a, c);
    isEquivalentUnderTransposition(a, a);
    '''
	-------------------------------------------------------------------------------------------------------- */
    isEquivalentUnderTransposition { |expr|
    	if (expr.isKindOf(this.species).not) { ^false };
    	if (this.size != expr.size) { ^false };
    	^((this.pitchNumbers - expr.pitchNumbers).asSet.size == 1);
    }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • makeNotes
    '''
	-------------------------------------------------------------------------------------------------------- */
	makeNotes {
		^this.notYetImplemented;
	}
	/* --------------------------------------------------------------------------------------------------------
     '''
     • multiply
	''Multiplies pitch segment.
	
	Returns new pitch segment.
   	
     code::
   	a = FoscPitchSegment([60, 61, 62]);
	b = a.multiply(3);
	b.pitchNumbers;
     '''
	-------------------------------------------------------------------------------------------------------- */
	multiply { |n|
		^this.species.new(this.items.collect { |each| each.multiply(n) });
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • reverse
	'''Retrograde of pitch segment.

	Returns new pitch segment.
  	'''

    code::
 	a = FoscPitchSegment([60, 61, 62]);
	b = a.retrograde;
	b.pitchNumbers;
    '''
	-------------------------------------------------------------------------------------------------------- */
	reverse {
		^this.species.new(this.items.reverse);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • rotate
	'''Rotates pitch segment.

	Returns new pitch segment.
	'''

    code::
	a = FoscPitchSegment([60, 61, 62]);
	b = a.rotate(-1);
	b.pitchNumbers;

    code::
	a = FoscPitchSegment([60, 61, 62]);
	b = a.rotate(-1, transpose: true);
	b.pitchNumbers;
    '''
	-------------------------------------------------------------------------------------------------------- */
	rotate { |n, transpose=false|
		var rotatedPitches, newSegment, interval;
		rotatedPitches = this.items.rotate(n);
		newSegment = this.species.new(rotatedPitches);
		if (transpose) {
			//!!! TODO interval = newSegment[0] - this[0];
			interval = this[0].pitchNumber - newSegment[0].pitchNumber;
			if (this[0] != newSegment[0]) { newSegment = newSegment.transpose(interval) };
		};
		^newSegment;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • transpose
	'''Transposes pitch segment by `expr`.

	Returns new pitch segment.
	'''

    code::
	a = FoscPitchSegment([60, 61, 62]);
	b = a.transpose(1);
	b.pitchNumbers;

    code::
    a = FoscPitchSegment([60, 61, 62]);
    b = a.transpose([12, 14, 16]);
    b.pitchNumbers;
    '''
	-------------------------------------------------------------------------------------------------------- */
	transpose { |expr|
        if (expr.isSequenceableCollection) {
            ^this.species.new(this.items.collect { |each, i| each.transpose(expr.wrapAt(i)) });
        } {
            ^this.species.new(this.items.collect { |each| each.transpose(expr) });
        };
	}
	/* --------------------------------------------------------------------------------------------------------
 '''
	• hasDuplicates
	'''True if pitch segment has duplicate items. Otherwise false.
	
	Returns true or false.
	'''

 code::
 	a = FoscPitchSegment([60, 61, 62]);
 	a.hasDuplicates;

 code::
 	a = FoscPitchSegment([60, 60, 61, 62]);
 	a.hasDuplicates;
 '''
	-------------------------------------------------------------------------------------------------------- */
	hasDuplicates {
		^(FoscPitchSet(this.items).size < this.size);
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • midicps (abjad: hertz)
	'''Gets hertz of pitches in pitch segment.
	
	Returns tuple.
	'''
	
    code::
	a = FoscPitchSegment([60, 61, 62]);
	a.midicps;
    '''
	-------------------------------------------------------------------------------------------------------- */
	midicps {
		^this.items.collect { |each| each.midicps };
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • inflectionPointCount

    Inflection point count of pitch segment.

	Returns nonnegative integer.
	
    code::
	a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
  	a.inflectionPointCount.postln;

    post::
    POSTOUTPUT
    '''
    '''
	-------------------------------------------------------------------------------------------------------- */
	inflectionPointCount {
		^(this.localMinima.size + this.localMaxima.size);
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • localMaxima
    
    Local maxima of pitch segment.

	Returns array.

    code::
  	a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
  	a.localMaxima.collect { |each| each.pitchNumber }.postln;

    post::
    POSTOUTPUT
    '''
    '''
	-------------------------------------------------------------------------------------------------------- */
	localMaxima {
		var result, left, middle, right;
		result = [];
		if (this.size > 3) {
			(this.size - 2).do { |i|
				# left, middle, right = this.atAll((i .. (i + 2)));
				if (left < middle && { right < middle }) { result = result.add(middle) };
			};
		};
		^result;
	}
    /* --------------------------------------------------------------------------------------------------------
    '''
    • localMinima
    
    Local minima of pitch segment.

    code::
 	Returns array;
	
    code::
	a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
  	a.localMinima.collect { |each| each.pitchNumber }.postln;

    post::
    POSTOUTPUT
    '''
    '''
	-------------------------------------------------------------------------------------------------------- */
	localMinima {
		var result, left, middle, right;
		result = [];
		if (this.size > 3) {
			(this.size - 2).do { |i|
				# left, middle, right = this.atAll((i .. (i + 2)));
				if (middle < left && { middle < right }) { result = result.add(middle) };
			};
		};
		^result;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • maxItem

    code::
	a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
  	a.maxItem.pitchNumber.postln;

    post::
    POSTOUTPUT
    '''
    '''
	-------------------------------------------------------------------------------------------------------- */
	maxItem { |func|
		^this.items.maxItem(func);
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • minItem

    code::
	a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
  	a.minItem.pitchNumber.postln;

    post::
    POSTOUTPUT
    '''
    '''
	-------------------------------------------------------------------------------------------------------- */
	minItem { |func|
		^this.items.minItem(func);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS: DISPLAY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
 '''
	• illustrate

 code::
    a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
    a.illustrate.format;
    a.show;

 img:: ![](../img/pitch-pitch-segment-1.png)
 '''

 p = "%/fosc/docs/img/pitch-pitch-segment-1".format(Platform.userExtensionDir);
 a.writePNG("%.ly".format(p));



 '''
	-------------------------------------------------------------------------------------------------------- */
 	illustrate {
        ^FoscLilypondFile.pitch([this]);
    }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • inspect
    '''
	-------------------------------------------------------------------------------------------------------- */
	// inspect {
	// 	//^this.notYetImplemented(thisMethod);
	// 	collection.do { |each| each.inspect };
	// }
	/* --------------------------------------------------------------------------------------------------------
    '''
    • play
    '''
	-------------------------------------------------------------------------------------------------------- */
	play {
		var selection;
        selection = FoscLeafMaker().(this, [1/4]);
        player = selection.play;
	}
	/* --------------------------------------------------------------------------------------------------------
    '''
    • show

    code::
    a = FoscPitchSegment([60, 61, 62, 63, 64, 65, 66, 68, 81, 65, 59, 63, 84, 60]);
    a.show;

    img:: ![](../img/pitch-pitch-segment-2.png)
    '''

    p = "%/fosc/docs/img/pitch-pitch-segment-2".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
	-------------------------------------------------------------------------------------------------------- */
	// show {
	// 	^this.illustrate.show;
	// }
}
