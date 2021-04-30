/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscStaffPosition


SUMMARY:: Returns a FoscStaffPosition.


DESCRIPTION:: Staff position


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStaffPosition : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <number;
    *new { |number=0|
        if (number.isKindOf(FoscStaffPosition)) { number = number.number };
        assert(number.isNumber);
        ^super.new.init(number);
    }
    init { |argNumber|
        number = argNumber;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • ==

    Is true when 'object' is a staff position with the same number as this staff position.
    -------------------------------------------------------------------------------------------------------- */
    == { |object|
        if (object.isKindOf(this.species).not) { ^false };
        ^(number == object.number);
    }
    /* --------------------------------------------------------------------------------------------------------
    • <

    Is true when staff position is less than 'object'.
    -------------------------------------------------------------------------------------------------------- */
    < { |object|
        if (object.isKindOf(this.species).not) { ^false };
        ^(number < object.number);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"%(%)".format(this.species, number);
    }
    /* --------------------------------------------------------------------------------------------------------
    • hash
    !!!TODO: not yet implemented
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • str

    '''
    code::
    a = FoscStaffPosition(1);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"%(%)".format(this.species, number);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • number
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • toPitch

    Makes named pitch from staff position and 'clef'.

    '''
    Treble clef.

    code::
    (-6..5).collect { |i| FoscStaffPosition(i).toPitch('treble').str };
    '''

    '''
    Bass clef.

    code::
    (-6..5).collect { |i| FoscStaffPosition(i).toPitch('bass').str };
    '''

    '''
    Alto clef.

    code::
    (-6..5).collect { |i| FoscStaffPosition(i).toPitch('alto').str };
    '''

    '''
    Percussion clef.

    code::
    (-6..5).collect { |i| FoscStaffPosition(i).toPitch('percussion').str };
    '''
    -------------------------------------------------------------------------------------------------------- */
    toPitch { |clef='treble'|
        var offsetStaffPositionNumber, offsetStaffPosition, octaveNumber, diatonicPCNumber, pitchClassNumber;
        var pitchNumber, pitch;

        clef = FoscClef(clef);
        offsetStaffPositionNumber = number;
        offsetStaffPositionNumber = offsetStaffPositionNumber - clef.middleCPosition.number;
        offsetStaffPosition = this.species.new(offsetStaffPositionNumber);
        octaveNumber = offsetStaffPosition.number.div(7) + 4;
        diatonicPCNumber = offsetStaffPosition.number % 7;
        pitchClassNumber = FoscPitchNameManager.diatonicPCNumberToDiatonicPitchClassNumber(diatonicPCNumber);
        pitchNumber = 12 * (octaveNumber + 1);
        pitchNumber = pitchNumber + pitchClassNumber;
        pitch = FoscPitch(pitchNumber);
        ^pitch;
    }
}
