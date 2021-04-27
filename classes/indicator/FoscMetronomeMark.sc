/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscMetronomeMark


SUMMARY:: Returns a FoscMetronomeMark.


DESCRIPTION:: Tempo indicator.


USAGE::

'''
Initialize integer-valued metronome mark.

code::
a = FoscScore();
b = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.add(b);
m = FoscMetronomeMark(#[1,4], 90);
b[0].attach(m);
a.show;
'''

'''
!!!TODO: incomplete implementation

Initialize rational-valued metronome mark.

code::
a = FoscScore();
b = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.add(b);
m = FoscMetronomeMark(#[1,4], FoscFraction(182, 2));
b[0].attach(m);
a.show;
nointerpret
'''

'''
Initialize from text, duration, and range.

code::
a = FoscScore();
b = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.add(b);
m = FoscMetronomeMark(#[1,4], #[120, 133], "Quick");
b[0].attach(m);
a.show;
'''

'''
!!!TODO: incomplete implementation

Use rational-value units-per-minute together with custom markup for float-valued metronome marks.

code::
a = FoscScore();
b = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.add(b);
t = FoscMetronomeMark.makeTempoEquationMarkup(#[1,4], 90.1);
m = FoscMetronomeMark(#[1,4], FoscFraction(#[900,10]), customMarkup: t);
b[0].attach(m);
a.show;
nointerpret
'''
------------------------------------------------------------------------------------------------------------ */
FoscMetronomeMark : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <customMarkup, <hide, <referenceDuration, <textualIndication, <unitsPerMinute;
    var <context='Score', <formatSlot='opening', <mutatesOffsetsInSeconds=true, <parameter='METRONOME_MARK';
    var <persistent=true;
    *new { |referenceDuration, unitsPerMinute, textualIndication, customMarkup, hide=false|
        ^super.new.init(referenceDuration, unitsPerMinute, textualIndication, customMarkup, hide);
    }
    init { |argReferenceDuration, argUnitsPerMinute, argTextualIndication, argCustomMarkup, argHide|
        //!!! INCOMPLETE
        referenceDuration = argReferenceDuration ?? { FoscDuration(1, 4) };
        referenceDuration = FoscDuration(referenceDuration);
        unitsPerMinute = argUnitsPerMinute ? 60;
        textualIndication = argTextualIndication;
        customMarkup = argCustomMarkup;
        hide = argHide;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • + (abjad: __add__)
    -------------------------------------------------------------------------------------------------------- */
    add {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • / (abjad: __div__)
    -------------------------------------------------------------------------------------------------------- */
    div {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • format
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.prGetLilypondFormat;
    }
    /* --------------------------------------------------------------------------------------------------------
    • == (not in abjad ?)

    '''
    code::
    a = FoscMetronomeMark([1, 4], 72);
    b = FoscMetronomeMark([1, 4], 72);
    c = FoscMetronomeMark([1, 8], 72);
    d = FoscMetronomeMark([1, 4], 60);
    (a == b).postln;         // true

    code::
    (a == c).postln;         // false

    code::
    (a == d).postln;         // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        if (expr.isKindOf(this.species).not) { ^false };
        if (referenceDuration != expr.referenceDuration) { ^false };
        if (unitsPerMinute != expr.unitsPerMinute) { ^false };
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    • < (abjad: __lt__)
    -------------------------------------------------------------------------------------------------------- */
    < {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • * (abjad __mul__)
    -------------------------------------------------------------------------------------------------------- */
    mul {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • (abjad: __rmul__)
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    • asCompileString (abjad: __str__)
    -------------------------------------------------------------------------------------------------------- */
    str {
        var string;
        string = "% = %".format(referenceDuration.str, unitsPerMinute);
        ^string;
    }
    /* --------------------------------------------------------------------------------------------------------
    • - (abjad: __sub__)
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    • (abjad: __truediv__)
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *makeTempoEquationMarkup
    -------------------------------------------------------------------------------------------------------- */
    *makeTempoEquationMarkup { |referenceDuration, unitsPerMinute|
        var selection, maker, lhsScoreMarkup, equalMarkup, rhsMarkup, markup;
        if (referenceDuration.isKindOf(FoscSelection)) {
            selection = referenceDuration;
        } {
            maker = FoscLeafMaker();
            selection = maker.([60], referenceDuration);
        };
        lhsScoreMarkup = FoscDuration.toScoreMarkup(selection);
        lhsScoreMarkup = lhsScoreMarkup.scale(#[0.75, 0.75]);
        equalMarkup = FoscMarkup("=");
        if (
            unitsPerMinute.isKindOf(FoscFraction)
            && { unitsPerMinute.isIntegerEquivalentNumber.not }
        ) {
            rhsMarkup = FoscMarkup.makeImproperFractionMarkup(unitsPerMinute);
            rhsMarkup = rhsMarkup.raise_(-0.5);
        } {
            rhsMarkup = FoscMarkup(unitsPerMinute);
            rhsMarkup = rhsMarkup.generalAlign('Y', -0.5);
        };
        markup = lhsScoreMarkup ++ equalMarkup ++ rhsMarkup;
        ^markup;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • durationToSeconds (abjad: duration_to_milliseconds)

    '''
    A quarter-note lasts a second at quarter equals 60.

    code::
    a = FoscMetronomeMark(#[1,4], 60);
    a.durationToSeconds(1/4).asFloat.postln;
    '''

    '''
    A quarter-note lasts 2/3 of a second at quarter equals 60.

    code::
    a = FoscMetronomeMark(#[1,4], 90);
    a.durationToSeconds(1/4).asFloat.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    durationToSeconds { |duration|
        var multiplier;
        duration = FoscDuration(duration);
        multiplier = FoscMultiplier(*referenceDuration.pair.reverse) * FoscMultiplier(60, unitsPerMinute);
        duration = (duration * multiplier).asFloat;
        ^duration;
    }
    /* --------------------------------------------------------------------------------------------------------
    • listRelatedTempos (abjad: list_related_tempos)
    -------------------------------------------------------------------------------------------------------- */
    listRelatedTempos {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • unitDuration (not in abjad -- for compatibility with SuperCollider)

    '''
    code::
    a = FoscMetronomeMark([1, 4], 60);
    a.unitDuration.asFloat.postln;
    '''

    '''
    code::
    a = FoscMetronomeMark([1, 4], 120);
    a.unitDuration.asFloat.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    unitDuration {
        ^this.durationToSeconds(FoscDuration(1, 4));
    }
    /* --------------------------------------------------------------------------------------------------------
    • unitTempo (not in abjad -- for compatibility with SuperCollider)

    '''
    code::
    a = FoscMetronomeMark([1, 4], 60);
    a.unitTempo.asFloat.postln;

    code::
    a = FoscMetronomeMark([1, 4], 120);
    a.unitTempo.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    unitTempo {
        ^this.unitDuration.reciprocal.asFloat;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prDotted
    -------------------------------------------------------------------------------------------------------- */
    prDotted {
        ^this.referenceDuration.lilypondDurationString;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prEquation
    '''
    code::
    a = FoscMetronomeMark(FoscDuration(1, 8), 96);
    a.prEquation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prEquation {
        var markup;
        if (this.referenceDuration.isNil) { ^nil };
        case
        { this.unitsPerMinute.isSequenceableCollection } {
            ^"%=%-%".format(this.prDotted, this.unitsPerMinute[0], this.unitsPerMinute[1]);
        }
        { this.unitsPerMinute.isFloat || { this.unitsPerMinute.isKindOf(FoscFraction) } } {
            markup = FoscMetronomeMark.makeTempoEquationMarkup(this.referenceDuration, this.unitsPerMinute);
            ^markup.str;
        };
        ^"%=%".format(this.prDotted, this.unitsPerMinute);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    '''
    code::
    a = FoscMetronomeMark(FoscDuration(1, 8), 96);
    a.prGetLilypondFormat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        var text, equation;
        if (this.textualIndication.notNil) {
            text = this.textualIndication;
            text = FoscScheme.formatSchemeValue(text);
        };
        if (this.referenceDuration.notNil && { this.unitsPerMinute.notNil }) {
            equation = this.prEquation;
        };
        case
        { this.customMarkup.notNil } {
            ^"\\tempo %".format(this.customMarkup);
        }
        { text.notNil && { equation.notNil } } {
            ^"\\tempo % %".format(text, equation);
        }
        { equation.notNil } {
            ^"\\tempo %".format(equation);
        }
        { text.notNil } {
            ^"\\tempo %".format(text);
        }
        { ^"\\tempo \\default" };
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
        var bundle;
        bundle = FoscLilypondFormatBundle();
        if (hide.not) {
            bundle.before.commands.add(this.prGetLilypondFormat);
        };
        ^bundle;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • customMarkup
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • isImprecise

    Is true if tempo is entirely textual or if tempo's units_per_minute is a range. Otherwise false.

    Returns true or false.

    '''
    code::
    FoscMetronomeMark(FoscDuration(1, 4), 60).isImprecise.postln;           // false

    code::
    FoscMetronomeMark(4, 60, 'Langsam').isImprecise.postln;                 // false

    code::
    FoscMetronomeMark(textualIndication: 'Langsam').isImprecise.postln;     // true

    code::
    FoscMetronomeMark(4, [35, 50], 'Langsam').isImprecise.postln;           // true

    code::
    FoscMetronomeMark(FoscDuration(1, 4), [35, 50]).isImprecise.postln;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    isImprecise {
        if (this.referenceDuration.notNil) {
            if (this.unitsPerMinute.notNil) {
                if (this.unitsPerMinute.isSequenceableCollection.not) {
                    ^false;
                };
            };
        };
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    • quartersPerMinute

    '''
    code::
    a = FoscMetronomeMark(FoscDuration(1, 8), 60);
    a.quartersPerMinute.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    //!!!INCOMPLETE
    quartersPerMinute {
        var result;
        // if (this.isImprecise) { ^nil };
        result = referenceDuration / FoscDuration(1, 4) * unitsPerMinute;
        result = result.asFloat;
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • referenceDuration
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • textualIndication
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • unitsPerMinute
    -------------------------------------------------------------------------------------------------------- */
}
