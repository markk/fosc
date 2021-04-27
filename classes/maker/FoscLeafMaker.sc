/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscLeafMaker


SUMMARY:: Returns a FoscLeafMaker.


DESCRIPTION:: TODO


USAGE::

'''

• FoscLeafMaker

Leaf-maker.

Returns a FoscSelection.


• Example 1

Integer and string elements in pitches result in notes.

code::
m = FoscLeafMaker().(#[62,64,"F#5","G#5"], [1/4]);
m.show;

img:: ![](../img/maker-leaf-maker-1.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-1".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 2

Tuple elements in pitches result in chords.

code::
m = FoscLeafMaker().(#[[60,62,64], ["F#5","G#5","A#5"]], [1/2]);
m.show;

img:: ![](../img/maker-leaf-maker-2.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-2".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));




!!!TODO: single string as chord not working
code::
m = FoscLeafMaker().(#[[60,62,64], "F#5 G#5 A#5"], [1/2]);
m.show;

img:: ![](../img/maker-leaf-maker-3.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-3".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 3

Nil-valued elements in 'pitches' result in rests.

code::
m = FoscLeafMaker().(nil, 1/4 ! 4);
m.show;

img:: ![](../img/maker-leaf-maker-4.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-4".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 4

Values passed to 'pitches' can be mixed and matched.

code::
m = FoscLeafMaker().(#[[60,62,64], nil, "C#5", "D#5"], [1/4]);
m.show;

img:: ![](../img/maker-leaf-maker-5.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-5".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 5

Works with pitch segments.

code::
m = FoscLeafMaker().(FoscPitchSegment(#["E5","Eb5","D5","Db5", "C5"]), [1/4]);
m.show;

img:: ![](../img/maker-leaf-maker-6.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-6".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 6

Reads pitches cyclically when the length of 'pitches' is less than the length of 'durations'.

code::
m = FoscLeafMaker().(#[72], [3/8, 1/8, 3/8, 1/8]);
m.show;

img:: ![](../img/maker-leaf-maker-7.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-7".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 7

Reads durations cyclically when the length of durations is less than the length of pitches.

code::
m = FoscLeafMaker().(#[72,74,76,77], [1/4]);
m.show;

img:: ![](../img/maker-leaf-maker-8.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-8".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 8

Elements in durations with non-power-of-two denominators result in tuplet-nested leaves.

code::
m = FoscLeafMaker().(#[60,62,64,65], [1/4, 1/12, 1/6, 1/2]);
m.show;

img:: ![](../img/maker-leaf-maker-9.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-9".format(Platform.userExtensionDir);
m.writePNG("%.ly".format(p));





• Example 9

Set 'increaseMonotonic' to false to return nonassignable durations tied from greatest to least.

code::
m = FoscLeafMaker(increaseMonotonic: false).(#["E5"], [13/16]);
x = FoscStaff(m);
x[0].attach(FoscTimeSignature(#[13, 16]));
x.show;

img:: ![](../img/maker-leaf-maker-10.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-10".format(Platform.userExtensionDir);
x.writePNG("%.ly".format(p));





• Example 10

Set 'increaseMonotonic' to true to return nonassignable durations tied from least to greatest.

code::
m = FoscLeafMaker(increaseMonotonic: true).(#["E5"], [13/16]);
x = FoscStaff(m);
x[0].attach(FoscTimeSignature(#[13, 16]));
x.show;

img:: ![](../img/maker-leaf-maker-11.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-11".format(Platform.userExtensionDir);
x.writePNG("%.ly".format(p));





• Example 11 !!!TODO: update to abjad 3.0 documentation

Set 'forbiddenNoteDuration' to avoid notes greater than or equal to a certain written duration.

code::
m = FoscLeafMaker(forbiddenNoteDuration: FoscDuration(1, 2)).(#[65,67], [5/8]);
x = FoscStaff(m);
x[0].attach(FoscTimeSignature(#[5, 4]));
x.show;

img:: ![](../img/maker-leaf-maker-12.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-12".format(Platform.userExtensionDir);
x.writePNG("%.ly".format(p));





• Example 12

forbiddenWrittenDuration and increaseMonotonic may be set together.

code::
m = FoscLeafMaker(increaseMonotonic: true, forbiddenNoteDuration: FoscDuration(1, 2));
m = m.(#[65,67], [5/8]);
x = FoscStaff(m);
x[0].attach(FoscTimeSignature(#[5, 4]));
x.show;

img:: ![](../img/maker-leaf-maker-13.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-13".format(Platform.userExtensionDir);
x.writePNG("%.ly".format(p));





• Example 14

Nil-valued elements in 'pitches' result in multimeasure rests when the multimeasure rest keyword is set.

code::
m = FoscLeafMaker(useMultimeasureRests: true).(#[nil], [3/8, 5/8]);
x = FoscStaff(m, lilypondType: 'RhythmicStaff');
x.leafAt(0).attach(FoscTimeSignature(#[3, 8]));
x.leafAt(1).attach(FoscTimeSignature(#[5, 8]));
x.show;

img:: ![](../img/maker-leaf-maker-14.png)
'''

p = "%/fosc/docs/img/maker-leaf-maker-14".format(Platform.userExtensionDir);
x.writePNG("%.ly".format(p));





• Example 16

Make skips instead of rests.

code::
m = FoscLeafMaker(skipsInsteadOfRests: true).([nil], [13/16]);
x = FoscStaff(m);
x.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscLeafMaker {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <increaseMonotonic, <forbiddenNoteDuration, <forbiddenRestDuration, <metricalHierarchy;
    var <skipsInsteadOfRests, <repeatTies, <useMultimeasureRests, <tag;
    *new { |increaseMonotonic=false, forbiddenNoteDuration, forbiddenRestDuration, metricalHierarchy,
        skipsInsteadOfRests=false, repeatTies=false, useMultimeasureRests=false, tag|

        if (forbiddenNoteDuration.notNil) { forbiddenNoteDuration = FoscDuration(forbiddenNoteDuration) };
        if (forbiddenRestDuration.notNil) { forbiddenRestDuration = FoscDuration(forbiddenRestDuration) };
        if (tag.notNil) { assert([Symbol, String].any { |type| tag.isKindOf(type) }) };

        ^super.new.init(increaseMonotonic, forbiddenNoteDuration, forbiddenRestDuration, metricalHierarchy,
            skipsInsteadOfRests, repeatTies, useMultimeasureRests, tag)
    }
    init { |argIncreaseMonotonic, argForbiddenNoteDuration, argForbiddenRestDuration, argMetricalHierarchy,
        argSkipsInsteadOfRests, argRepeatTies, argUseMultimeasureRests, argTag|

        increaseMonotonic = argIncreaseMonotonic;
        forbiddenNoteDuration = argForbiddenNoteDuration;
        forbiddenRestDuration = argForbiddenRestDuration;
        metricalHierarchy = argMetricalHierarchy;
        skipsInsteadOfRests = argSkipsInsteadOfRests;
        repeatTies = argRepeatTies;
        useMultimeasureRests = argUseMultimeasureRests;
        tag = argTag;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • forbiddenNoteDuration

    Gets forbidden note duration.

    Returns duration or nil.
    '''
    -------------------------------------------------------------------------------------------------------- */
     /* --------------------------------------------------------------------------------------------------------
    '''
    • forbiddenRestDuration

    Gets forbidden rest duration.

    Returns duration or nil.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • increaseMonotonic

    Is true when durations increase monotonically. Otherwise false.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • metricalHierarchy

    Gets metrical hierarchy.

    Returns metrical hierarchy or none.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • skipsInsteadOfRests

    Is true when skips appear in place of rests. Otherwise false.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • repeatTies

    Is true when ties are repeat ties.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • useMultimeasureRests

    Is true when rests are multimeasure.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tag

    Gets tag.
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • value

    Calls leaf-maker on pitches and durations.

    Returns selection.
    '''
    -------------------------------------------------------------------------------------------------------- */
    value { |pitches, durations|
        var nonreducedFractions, size, durationGroups, result, factors, currentPitches, leaves;
        var denominator, numerator, multiplier, ratio, tupletLeaves, tuplet;

        pitches = FoscPitchParser(pitches);
        if (durations.isSequenceableCollection.not) { durations = [durations] };
        nonreducedFractions = durations.collect { |each| FoscNonreducedFraction(each) };
        size = [pitches.size, nonreducedFractions.size].maxItem;
        nonreducedFractions = nonreducedFractions.wrapExtend(size);
        pitches = pitches.wrapExtend(size);
        durationGroups = FoscDuration.prGroupByImpliedProlation(nonreducedFractions);
        result = [];

        durationGroups.do { |durationGroup|
            // get factors in denominator of duration group duration not 1 or 2
            factors = durationGroup[0].denominator.factors;
            factors = factors.as(Set);
            factors.remove(1);
            factors.remove(2);
            currentPitches = pitches[0..(durationGroup.size - 1)];
            pitches = pitches[durationGroup.size..];

            if (factors.size == 0) {
                currentPitches.do { |pitch, i|
                    if (pitch.isKindOf(FoscPitchSegment)) { pitch = pitch.pitches };
                    leaves = FoscLeafMaker.prMakeLeafOnPitch(
                        pitch: pitch,
                        duration: durationGroup[i],
                        increaseMonotonic: increaseMonotonic,
                        forbiddenNoteDuration: forbiddenNoteDuration,
                        forbiddenRestDuration: forbiddenRestDuration,
                        skipsInsteadOfRests: skipsInsteadOfRests,
                        repeatTies: repeatTies,
                        useMultimeasureRests: useMultimeasureRests,
                        tag: tag
                    );
                    result = result.addAll(leaves.items);
                    //result = result.addAll(leaves);
                };
            } {
                denominator = durationGroup[0].denominator;
                if (denominator.isPowerOfTwo) {
                    numerator = denominator;
                } {
                    numerator = denominator.previousPowerOf(2);
                };
                multiplier = [numerator, denominator].asInteger;
                ratio = 1 / FoscDuration(*multiplier);

                durationGroup = durationGroup.collect { |duration| ratio * FoscDuration(duration) };

                // make tuplet leaves
                tupletLeaves = [];
                currentPitches.do { |pitch, i|
                    leaves = FoscLeafMaker.prMakeLeafOnPitch(
                        pitch: pitch,
                        duration: durationGroup[i],
                        increaseMonotonic: increaseMonotonic,
                        skipsInsteadOfRests: skipsInsteadOfRests,
                        repeatTies: repeatTies,
                        useMultimeasureRests: useMultimeasureRests,
                        tag: tag
                    );
                    tupletLeaves = tupletLeaves.addAll(leaves.items);
                    //tupletLeaves = tupletLeaves.addAll(leaves);
                };
                tuplet = FoscTuplet(multiplier, tupletLeaves);
                result = result.add(tuplet);
            };
        };

        result = FoscSelection(result);
        ^result;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prMakeLeafOnPitch
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prMakeLeafOnPitch { |pitch, duration, increaseMonotonic=false, forbiddenNoteDuration,
        forbiddenRestDuration, skipsInsteadOfRests=false, repeatTies=false, useMultimeasureRests=false, tag|

        var notePrototype, chordPrototype, restPrototype, leaves, multimeasureRest, multiplier;

        notePrototype = [Number, String, FoscPitch, FoscPitchClass];
        chordPrototype = [SequenceableCollection, FoscPitchSegment];
        restPrototype = [Nil];

        case
        { notePrototype.any { |prototype| pitch.isKindOf(prototype) } } {
            leaves = FoscLeafMaker.prMakeTiedLeaf(
                class: FoscNote,
                duration: duration,
                increaseMonotonic: increaseMonotonic,
                forbiddenDuration: forbiddenNoteDuration,
                pitches: pitch,
                repeatTies: repeatTies,
                tag: tag
            );
        }
        { chordPrototype.any { |prototype| pitch.isKindOf(prototype) } } {
            leaves = FoscLeafMaker.prMakeTiedLeaf(
                class: FoscChord,
                duration: duration,
                increaseMonotonic: increaseMonotonic,
                forbiddenDuration: forbiddenNoteDuration,
                pitches: pitch,
                repeatTies: repeatTies,
                tag: tag
            );
        }
        { restPrototype.any { |prototype| pitch.isKindOf(prototype) } && skipsInsteadOfRests } {
            leaves = FoscLeafMaker.prMakeTiedLeaf(
                class: FoscSkip,
                duration: duration,
                increaseMonotonic: increaseMonotonic,
                forbiddenDuration: forbiddenRestDuration,
                repeatTies: repeatTies,
                tag: tag
            );
        }
        { restPrototype.any { |prototype| pitch.isKindOf(prototype) } && useMultimeasureRests.not } {
            leaves = FoscLeafMaker.prMakeTiedLeaf(
                class: FoscRest,
                duration: duration,
                increaseMonotonic: increaseMonotonic,
                forbiddenDuration: forbiddenRestDuration,
                repeatTies: repeatTies,
                tag: tag
            );
        }
        { restPrototype.any { |prototype| pitch.isKindOf(prototype) } && useMultimeasureRests } {
            multimeasureRest = FoscMultimeasureRest(1);
            multimeasureRest.multiplier_(duration);
            leaves = FoscSelection([multimeasureRest]);
        }
        {
            error("%:value: unknown pitch: pitch".format(this.species, pitch.str));
        };
        ^leaves;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prMakeTiedLeaf
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prMakeTiedLeaf { |class, duration, increaseMonotonic=false, forbiddenDuration, multiplier, pitches,
        tieParts=true, repeatTies=false, tag|

        var numerators, result, denominators, denominator, forbiddenNumerator, preferredNumerator;
        var betterParts, parts, writtenDuration, args;

        duration = FoscDuration(duration);

        if (forbiddenDuration.notNil) {
            forbiddenDuration = FoscDuration(forbiddenDuration);
            if (forbiddenDuration.isAssignable.not) {
                throw("%: forbiddenDuration must be assignable: %."
                    .format(this.species, forbiddenDuration.str));
            };
            if (forbiddenDuration.numerator != 1) {
                throw("%: forbiddenDuration numerator must be 1: %."
                    .format(this.species, forbiddenDuration.str));
            };
        };
        // find preferred numerator of written durations if necessary
        if (forbiddenDuration.notNil && { forbiddenDuration <= duration }) {
            denominators = [2 * forbiddenDuration.denominator, duration.denominator];
            denominator = denominators.reduce('lcm');
            forbiddenDuration = FoscNonreducedFraction(forbiddenDuration);
            forbiddenDuration = forbiddenDuration.withDenominator(denominator);
            duration = FoscNonreducedFraction(duration);
            duration = duration.withDenominator(denominator);
            forbiddenNumerator = forbiddenDuration.numerator;
            assert((forbiddenNumerator % 2) == 0);
            preferredNumerator = forbiddenNumerator / 2;
        };
        // make written duration numerators
        numerators = [];
        parts = duration.numerator.partitionIntoCanonicParts;

        if (forbiddenDuration.notNil && { forbiddenDuration <= duration }) {
            parts.do { |part|
                if (forbiddenNumerator <= part) {
                    betterParts = part.partitionIntoPartsLessThanDouble(preferredNumerator);
                    numerators = numerators.addAll(betterParts);
                } {
                    numerators = numerators.add(part);
                };
            };
        } {
            numerators = parts;
        };
        // reverse numerators if necessary
        if (increaseMonotonic) { numerators = numerators.reverse };
        // make one leaf per written duration
        result = [];
        numerators.do { |numerator|
            writtenDuration = FoscDuration(numerator, duration.denominator);
            if (pitches.notNil) {
                args = [pitches, writtenDuration];
            } {
                args = [writtenDuration];
            };
            result = result.add(class.new(*args));
        };
        result = FoscSelection(result);
        // apply tie spanner if required
        if (tieParts && { 1 < result.size } && { result[0].isPitched }) { result.tie(repeat: repeatTies) };
        ^result;
    }
}
