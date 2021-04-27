/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscTimespan


SUMMARY:: Returns a FoscTimespan.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTimespan

Timespan.

Timespans are closed-open intervals.

Timespans are immutable.
'''
------------------------------------------------------------------------------------------------------------ */
FoscTimespan : FoscObject {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <startOffset, <stopOffset;
    var <publishStorageFormat=true;
    *new { |startOffset= -inf, stopOffset=inf|
        ^super.new.init(startOffset, stopOffset);
    }
    init { |argStartOffset, argStopOffset|
        startOffset = this.prInitializeOffset(argStartOffset);
        stopOffset = this.prInitializeOffset(argStopOffset);
        if (stopOffset < startOffset) {
            throw("%::new: stopOffset % is less than startOffset %."
                .format(this.species, stopOffset.str, startOffset.str));
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • axis

    Arithmetic mean of 'startOffset' and 'stopOffset'.

    code::
    FoscTimespan(0, 10).axis.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    axis {
        ^(startOffset + stopOffset) / 2;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • duration

    Duration of timespan.

    code::
    FoscTimespan(0, 10).duration.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    duration {
        ^(stopOffset - startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isWellFormed

    Is true when timespan start offset preceeds timespan stop offset.

    code::
    FoscTimespan(0, 10).isWellFormed;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isWellFormed {
        ^(startOffset < stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • offsets

    Timespan offsets.

    code::
    FoscTimespan(0, 10).offsets.collect { |each| each.cs };
    '''
    -------------------------------------------------------------------------------------------------------- */
    offsets {
        ^[startOffset, stopOffset];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startOffset

    Timespan start offset.

    code::
    FoscTimespan(0, 10).startOffset.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopOffset

    Timespan stop offset.

    code::
    FoscTimespan(0, 10).stopOffset.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • == (abjad: __eq__)

    Is true when 'timespan' is a timespan with equal offsets. Otherwise false:

    Returns true or false.

    code::
    FoscTimespan(1, 3) == FoscTimespan(1, 3);

    code::
    FoscTimespan(1, 3) == FoscTimespan(2, 3);
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |timespan|
        ^(this.offsets == timespan.offsets);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • >= (abjad: __ge__)

    Is true when 'argument' start offset is greater or equal to timespan start offset. Otherwise false:

    Returns true or false.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);

    code::
    b >= c;
    a >= b;
    '''
    -------------------------------------------------------------------------------------------------------- */
    >= { |timespan|
        if (timespan.stopOffset.isNil) { ^false };
        if (this.startOffset >= timespan.stopOffset) { ^true };
        if (
            this.startOffset == timespan.stopOffset
            && { this.stopOffset >= timespan.stopOffset }) {
            ^true;
        };
        ^(this.startOffset >= timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • > (abjad: __gt__)

    Is true when 'argument' start offset is greater than timespan start offset. Otherwise false:

    Returns true or false.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);

    code::
    b > c;
    a > b;
    '''
    -------------------------------------------------------------------------------------------------------- */
    > { |timespan|
        if (timespan.stopOffset.isNil) { ^false };
        if (this.startOffset > timespan.stopOffset) { ^true };
        if (
            this.startOffset == timespan.stopOffset
            && { this.stopOffset > timespan.stopOffset }) {
            ^true;
        };
        ^(this.startOffset > timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • <= (abjad: __le__)

    Is true when 'argument' start offset is less than or equal to timespan start offset. Otherwise false:

    Returns true or false.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);

    code::
    b <= c;
    a <= b;
    '''
    -------------------------------------------------------------------------------------------------------- */
    <= { |timespan|
        if (timespan.stopOffset.isNil) { ^false };
        if (this.startOffset <= timespan.stopOffset) { ^true };
        if (
            this.startOffset == timespan.stopOffset
            && { this.stopOffset <= timespan.stopOffset }) {
            ^true;
        };
        ^(this.startOffset <= timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • < (abjad: __lt__ )

    Is true when 'argument' start offset is less than timespan start offset. Otherwise false:

    Returns true or false.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);

    code::
    b < c;
    a < b;
    '''
    -------------------------------------------------------------------------------------------------------- */
    < { |timespan|
        if (timespan.stopOffset.isNil) { ^false };
        if (this.startOffset < timespan.stopOffset) { ^true };
        if (
            this.startOffset == timespan.stopOffset
            && { this.stopOffset < timespan.stopOffset }) {
            ^true;
        };
        ^(this.startOffset < timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString

    Formats timespan.

    Returns string.

    code::
    FoscTimespan(1, 3).cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"%(%, %)".format(this.species, this.startOffset.str, this.stopOffset.str);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • format

    Formats timespan.

    Returns string.

    code::
    FoscTimespan(0, 10).format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^this.asCompileString;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • hash

    Hashes timespan.
    '''
    -------------------------------------------------------------------------------------------------------- */
    // hash {
    //     ^this.notYetImplemented(thisMethod);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • illustrate

    Illustrates timespan.

    Returns Lilypond file.
    '''
    -------------------------------------------------------------------------------------------------------- */
    illustrate { |range, scale=1|
        var timespans;
        timespans = FoscTimespanList([this]);
        ^timespans.illustrate(range: range, scale: scale);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • show
    '''
    -------------------------------------------------------------------------------------------------------- */
    show { |range, scale=1|
        ^this.illustrate(range: range, scale: scale).show;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • size

    Defined equal to 1 for all timespans.

    Returns positive integer.
    '''
    -------------------------------------------------------------------------------------------------------- */
    size {
        ^1;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SET OPERATIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • - (abjad: __sub__, -)

    Subtract 'timespan' from receiver.

    Returns timespan list.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);
    d = FoscTimespan(10, 20);

    code::
    x = a - a;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a - b;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a - c;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a - d;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b - a;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b - b;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b - c;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b - d;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c - a;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c - b;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c - c;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c - d;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = d - a;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = d - b;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = d - c;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = d - d;
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    - { |timespan|
        var result, newStartOffset, newStopOffset, newTimespan;

        result = FoscTimespanList();

        case { this.intersectsTimespan(timespan).not } {
            result.add(this.deepCopy);
        }
        { timespan.trisectsTimespan(this) } {
            newStartOffset = this.startOffset;
            newStopOffset = timespan.startOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);

            newStartOffset = timespan.stopOffset;
            newStopOffset = this.stopOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);
        }
        { timespan.containsTimespanImproperly(this) } {
            // pass
        }
        { timespan.overlapsOnlyStartOfTimespan(this) } {
            newStartOffset = timespan.stopOffset;
            newStopOffset = this.stopOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);
        }
        { timespan.overlapsOnlyStopOfTimespan(this) } {
            newStartOffset = this.startOffset;
            newStopOffset = timespan.startOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);
        }
        { timespan.stopsBeforeTimespanStops(this) } {
            newStartOffset = timespan.stopOffset;
            newStopOffset = this.stopOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);
        }
        { timespan.stopsWhenTimespanStops(this) } {
            newStartOffset = this.startOffset;
            newStopOffset = timespan.startOffset;
            newTimespan = this.species.new(newStartOffset, newStopOffset);
            result.add(newTimespan);
        }
        {
            ^throw("%:%: value error: *".format(this.species, thisMethod.name, timespan));
        };

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • sect (abjad: __and__, &)

    Intersection of receiver and 'timespan' (Logical AND).

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);
    d = FoscTimespan(10, 20);

    code::
    x = a.sect(b);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.sect(c);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.sect(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.sect(c);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.sect(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c.sect(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    sect { |timespan|
        var newStartOffset, newStopOffset, newTimespan;

        if (this.intersectsTimespan(timespan).not) { ^FoscTimespanList() };
        newStartOffset = [this.startOffset, timespan.startOffset].maxItem;
        newStopOffset = [this.stopOffset, timespan.stopOffset].minItem;
        newTimespan = this.species.new(newStartOffset, newStopOffset);

        ^FoscTimespanList([newTimespan]);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • symmetricDifference (abjad: __xor__, ^)

    Symmetric difference of receiver and 'timespan' (Logical XOR).

    Returns timespan list.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);
    d = FoscTimespan(10, 20);

    code::
    x = a.symmetricDifference(b);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.symmetricDifference(c);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.symmetricDifference(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.symmetricDifference(c);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.symmetricDifference(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c.symmetricDifference(d);
    x.do { |e| e.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    symmetricDifference { |timespan|
        var result, startOffsets, stopOffsets, timespan1, timespan2;

        result = FoscTimespanList();

        if (
            this.intersectsTimespan(timespan).not
            || { this.isTangentToTimespan(timespan) }
        ) {
            result.add(this.deepCopy);
            result.add(timespan.deepCopy);
            result.sort;
            ^result;
        };

        startOffsets = [this.startOffset, timespan.startOffset].sort;
        stopOffsets = [this.stopOffset, timespan.stopOffset].sort;
        timespan1 = this.species.new(startOffsets[0], startOffsets[1]);
        timespan2 = this.species.new(stopOffsets[0], stopOffsets[1]);
        if (timespan1.isWellFormed) { result.add(timespan1) };
        if (timespan2.isWellFormed) { result.add(timespan2) };

        result.sort;

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • union (abjad: __or__, |)

    Union of receiver and 'timespan' (Logical OR).

    Returns timespan list.


    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);
    d = FoscTimespan(10, 20);

    code::
    x = a.union(b);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.union(c);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = a.union(d);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.union(c);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = b.union(d);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    code::
    x = c.union(d);
    x.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    union { |timespan|
        var result, newStartOffset, newStopOffset, newTimespan;

        if (
            this.intersectsTimespan(timespan).not
            && { this.isTangentToTimespan(timespan).not }
        ) {
            result = FoscTimespanList([this, timespan]);
            result = result.sort;
            ^result;
        };

        newStartOffset = [this.startOffset, timespan.startOffset].minItem;
        newStopOffset = [this.stopOffset, timespan.stopOffset].maxItem;
        newTimespan = this.species.new(newStartOffset, newStopOffset);

        ^FoscTimespanList([newTimespan]);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prAsPostscript

    code::
    a = FoscTimespan(0, 3);
    p = a.prAsPostscript(-1, 0.5, 15);
    p.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prAsPostscript { |postscriptXOffset, postscriptYOffset, postscriptScale|
        var start, stop, ps;

        start = this.startOffset.asFloat * postscriptScale;
        start = start - postscriptXOffset;

        stop = this.stopOffset.asFloat * postscriptScale;
        stop = stop - postscriptXOffset;

        ps = FoscPostscript();
        ps = ps.moveto(start, postscriptYOffset);
        ps = ps.lineto(stop, postscriptYOffset);
        ps = ps.stroke;
        ps = ps.moveto(start, postscriptYOffset + 0.75);
        ps = ps.lineto(start, postscriptYOffset - 0.75);
        ps = ps.stroke;
        ps = ps.moveto(stop, postscriptYOffset + 0.75);
        ps = ps.lineto(stop, postscriptYOffset - 0.75);
        ps = ps.stroke;

        ^ps;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prCanFuse

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 12);
    c = FoscTimespan(-2, 2);

    code::
    a.prCanFuse(b);
    a.prCanFuse(c);
    b.prCanFuse(c);
    '''
    -------------------------------------------------------------------------------------------------------- */
    prCanFuse { |timespan|
        if (timespan.isKindOf(this.species)) {
            ^(this.intersectsTimespan(timespan) || { this.stopsWhenTimespanStarts(timespan) })
        };
        ^false;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetTimespan
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetTimespan { |object|
        var startOffset, stopOffset, newTimespan;

        case
        { object.isKindOf(FoscTimespan) } {
            # startOffset, stopOffset = object.offsets;
        }
        { object.respondsTo('timespan') } {
            # startOffset, stopOffset = object.timespan.offsets;
        }
        { object.respondsTo('prGetTimespan') } {
            # startOffset, stopOffset = object.prGetTimespan.offsets;
        }
        {
            ^throw("%:%: value error: *".format(this.species, thisMethod.name, object));
        };

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', startOffset);
        newTimespan.instVarPut('stopOffset', stopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prInitializeOffset
    '''
    -------------------------------------------------------------------------------------------------------- */
    prInitializeOffset { |offset|
        //if ([-inf, inf].includes(offset)) { ^offset };
        ^FoscOffset(offset);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prGetOffsets
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prGetOffsets { |object|
        case
        { object.isKindOf(FoscTimespan) } {
           // pass
        }
        { object.respondsTo('timespan') } {
            object = object.timespan;
        }
        { object.respondsTo('prGetTimespan') } {
            object = object.prGetTimespan;
        }
        {
            ^throw("%:%: value error: *".format(this.species, thisMethod.name, object));
        };

        ^[object.startOffset, object.stopOffset];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prGetStartOffsetAndMaybeStopOffset
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prGetStartOffsetAndMaybeStopOffset { |object|
        var startOffset, stopOffset;

        case
        { object.isKindOf(FoscTimespan) } {
           // pass
        }
        { object.respondsTo('timespan') } {
            object = object.timespan;
        }
        { object.respondsTo('prGetTimespan') } {
            object = object.prGetTimespan;
        };

        if (object.respondsTo('startOffset')) {
            startOffset = object.startOffset;
        } {
            ^throw("%:%: value error: *".format(this.species, thisMethod.name, object));
        };

        stopOffset = object.stopOffset;

        ^[startOffset, stopOffset];
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *prImplementsTimespanInterface
    '''
    -------------------------------------------------------------------------------------------------------- */
    *prImplementsTimespanInterface { |timespan|
        if (
            timespan.respondsTo('startOffset')
            && { timespan.respondsTo('stopOffset') }
        ) {
            ^true;
        };
        if (timespan.respondsTo('prGetTimespan')) {
            ^true;
        };
        if (timespan.respondsTo('timespan')) {
            ^true;
        };
        ^false;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • containsTimespanImproperly

    Is true when timespan contains 'timespan' improperly.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 10);

    code::
    a.containsTimespanImproperly(a);
    a.containsTimespanImproperly(b);
    b.containsTimespanImproperly(a);
    b.containsTimespanImproperly(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    containsTimespanImproperly { |timespan|
        ^(
            this.startOffset <= timespan.startOffset
            && (timespan.stopOffset <= this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • curtailsTimespan

    Is true when timespan curtails 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 10);

    code::
    a.curtailsTimespan(a);
    a.curtailsTimespan(b);
    b.curtailsTimespan(a);
    b.curtailsTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    curtailsTimespan { |timespan|
        ^(
            timespan.startOffset < this.startOffset
            && (this.startOffset <= timespan.stopOffset)
            && (timespan.stopOffset <= this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • delaysTimespan

    Is true when timespan delays 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);
    c = FoscTimespan(10, 20);

    code::
    a.delaysTimespan(b);
    b.delaysTimespan(c);
    b.delaysTimespan(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    delaysTimespan { |timespan|
        ^(
            this.startOffset <= timespan.startOffset
            && (timespan.startOffset <= this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • getOverlapWithTimespan

    Gets duration of overlap with 'timespan'.

    code::
    a = FoscTimespan(0, 15);
    b = FoscTimespan(5, 10);
    c = FoscTimespan(6, 6);
    d = FoscTimespan(12, 22);

    code::
    a.getOverlapWithTimespan(a).cs;
    a.getOverlapWithTimespan(b).cs;
    a.getOverlapWithTimespan(c).cs;
    a.getOverlapWithTimespan(d).cs;
    b.getOverlapWithTimespan(b).cs;
    b.getOverlapWithTimespan(c).cs;
    b.getOverlapWithTimespan(d).cs;
    c.getOverlapWithTimespan(c).cs;
    c.getOverlapWithTimespan(d).cs;
    d.getOverlapWithTimespan(d).cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    getOverlapWithTimespan { |timespan|
        var result;

        result = this.sect(timespan).items.collect { |each| each.duration }.sum;
        result = FoscDuration(result);

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • happensDuringTimespan

    Is true when timespan happens during 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 10);

    code::
    a.happensDuringTimespan(a);
    a.happensDuringTimespan(b);
    b.happensDuringTimespan(a);
    b.happensDuringTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    happensDuringTimespan { |timespan|
        ^(
            timespan.startOffset <= this.startOffset
            && (this.stopOffset <= timespan.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • intersectsTimespan

    Is true when timespan intersects 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);
    c = FoscTimespan(10, 15);

    code::
    a.intersectsTimespan(a);
    a.intersectsTimespan(b);
    a.intersectsTimespan(c);
    '''
    -------------------------------------------------------------------------------------------------------- */
    intersectsTimespan { |timespan|
        ^(
            timespan.startOffset <= this.startOffset
            && { this.startOffset < timespan.stopOffset };
        ) || {
            this.startOffset <= timespan.startOffset
            && { timespan.startOffset < this.stopOffset }
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isCongruentToTimespan

    Is true when timespan is congruent to 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.isCongruentToTimespan(a);
    a.isCongruentToTimespan(b);
    b.isCongruentToTimespan(a);
    b.isCongruentToTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    isCongruentToTimespan { |timespan|
        ^(
            timespan.startOffset == this.startOffset
            && (timespan.stopOffset == this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isTangentToTimespan

    Is true when timespan is tangent to 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.isTangentToTimespan(a);
    a.isTangentToTimespan(b);
    b.isTangentToTimespan(a);
    b.isTangentToTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    isTangentToTimespan { |timespan|
        ^(
            this.stopOffset == timespan.startOffset
            || { timespan.stopOffset == this.startOffset }
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • overlapsAllOfTimespan

    Is true when timespan overlaps all of 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 6);
    c = FoscTimespan(5, 10);

    code::
    a.overlapsAllOfTimespan(a);
    a.overlapsAllOfTimespan(b);
    b.overlapsAllOfTimespan(c);
    '''
    -------------------------------------------------------------------------------------------------------- */
    overlapsAllOfTimespan { |timespan|
        ^(
            this.startOffset < timespan.startOffset
            && (timespan.stopOffset < this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • overlapsOnlyStartOfTimespan

    Is true when timespan overlaps only start of 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(-5, 5);
    c = FoscTimespan(4, 6);
    d = FoscTimespan(5, 15);

    code::
    a.overlapsOnlyStartOfTimespan(a);
    a.overlapsOnlyStartOfTimespan(b);
    a.overlapsOnlyStartOfTimespan(c);
    a.overlapsOnlyStartOfTimespan(d);
    '''
    -------------------------------------------------------------------------------------------------------- */
    overlapsOnlyStartOfTimespan { |timespan|
        ^(
            this.startOffset < timespan.startOffset
            && (timespan.startOffset < this.stopOffset)
            && (this.stopOffset <= timespan.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • overlapsOnlyStopOfTimespan

    Is true when timespan overlaps only stop of 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(-5, 5);
    c = FoscTimespan(4, 6);
    d = FoscTimespan(5, 15);

    code::
    a.overlapsOnlyStopOfTimespan(a);
    a.overlapsOnlyStopOfTimespan(b);
    a.overlapsOnlyStopOfTimespan(c);
    a.overlapsOnlyStopOfTimespan(d);
    '''
    -------------------------------------------------------------------------------------------------------- */
    overlapsOnlyStopOfTimespan { |timespan|
        ^(
            timespan.startOffset <= this.startOffset
            && (this.startOffset < timespan.stopOffset)
            && (timespan.stopOffset < this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • overlapsStartOfTimespan

    Is true when timespan overlaps start of 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(-5, 5);
    c = FoscTimespan(4, 6);
    d = FoscTimespan(5, 15);

    code::
    a.overlapsStartOfTimespan(a);
    a.overlapsStartOfTimespan(b);
    a.overlapsStartOfTimespan(c);
    a.overlapsStartOfTimespan(d);
    '''
    -------------------------------------------------------------------------------------------------------- */
    overlapsStartOfTimespan { |timespan|
        ^(
            this.startOffset < timespan.startOffset
            && (timespan.startOffset < this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • overlapsStopOfTimespan

    Is true when timespan overlaps stop of 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(-5, 5);
    c = FoscTimespan(4, 6);
    d = FoscTimespan(5, 15);

    code::
    a.overlapsStopOfTimespan(a);
    a.overlapsStopOfTimespan(b);
    a.overlapsStopOfTimespan(c);
    a.overlapsStopOfTimespan(d);
    '''
    -------------------------------------------------------------------------------------------------------- */
    overlapsStopOfTimespan { |timespan|
        ^(
            this.startOffset < timespan.stopOffset
            && (timespan.stopOffset < this.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • partitionByRatio (abjad: divide_by_ratio)

    Partitions timespan by 'ratio'.

    Returns array of timespans.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.partitionByRatio(#[1,2,1]);
    b.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    partitionByRatio { |ratio|
        var unitDuration, partDurations, startOffsets, offsetPairs, result;

        result = [];
        unitDuration = this.duration / ratio.sum;
        partDurations = ratio.collect { |each| each * unitDuration };
        startOffsets = [this.startOffset] ++ (partDurations.integrate + this.startOffset.asFloat);
        startOffsets.doAdjacentPairs { |a, b| result = result.add(this.species.new(a, b)) };

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • reflect

    Reflects timespan about 'axis'.

    Returns a new FoscTimespan.


    Reverse timespan about timespan axis.

    code::
    a = FoscTimespan(3, 6).reflect;
    a.cs;

    Reverse timespan about arbitrary axis.

    code::
    a = FoscTimespan(3, 6).reflect(10);
    a.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    reflect { |axis|
        var startDistance, stopDistance;

        axis = axis ?? { this.axis };
        startDistance = this.startOffset - axis;
        stopDistance = this.stopOffset - axis;

        ^this.setOffsets(axis - stopDistance, axis - startDistance);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • roundOffsets

    Rounds timespan offsets to multiple of 'multiplier'.

    Returns a new FoscTimespan.


    code::
    a = FoscTimespan(1/5, 4/5);
    b = a.roundOffsets(1);
    b.cs;

    code::
    a = FoscTimespan(1/5, 4/5);
    b = a.roundOffsets(2);
    b.cs;

    code::
    a = FoscTimespan(1/5, 4/5);
    b = a.roundOffsets(2, anchor: 'right');
    b.cs;

    code::
    a = FoscTimespan(1/5, 4/5);
    b = a.roundOffsets(2, anchor: 'right', mustBeWellFormed: false);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    roundOffsets { |multiplier, anchor='left', mustBeWellFormed=true|
        var newStartOffset, newStopOffset, newTimespan;

        multiplier = FoscMultiplier(multiplier).abs;

        newStartOffset = FoscOffset((this.startOffset / multiplier).asFloat.round * multiplier);
        newStopOffset = FoscOffset((this.stopOffset / multiplier).asFloat.round * multiplier);

        if (newStartOffset == newStopOffset && mustBeWellFormed) {
            if (anchor == 'left') {
                newStopOffset = newStopOffset + multiplier;
            } {
                newStartOffset = newStartOffset - multiplier;
            };
        };

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', newStartOffset);
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • scale

    Scales timespan by 'multiplier'.

    Returns a new FoscTimespan.


    Scale timespan relative to timespan start offset.

    code::
    a = FoscTimespan(3, 6);
    b = a.scale(2);
    b.cs;

    Scale timespan relative to timespan stop offset.

    code::
    a = FoscTimespan(3, 6);
    b = a.scale(2, anchor: 'right');
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    scale { |multiplier, anchor='left'|
        var newDuration, newStartOffset, newStopOffset, newTimespan;

        multiplier = FoscMultiplier(multiplier);

        if (multiplier <= 0) {
            ^throw("%:roundOffsets: multiplier must be greater than 0: %."
                .format(this.species, multiplier.cs));
        };

        newDuration = this.duration * multiplier;

        case
        { anchor == 'left' } {
            newStartOffset = this.startOffset;
            newStopOffset = this.startOffset + newDuration;
        }
        { anchor == 'right' } {
            newStopOffset = this.stopOffset;
            newStartOffset = this.stopOffset - newDuration;
        } {
            ^throw("%:scale: unknown anchor direction: %.".format(this.species, anchor));
        };

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', newStartOffset);
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setDuration

    Sets timespan duration to 'duration'.

    Returns a new FoscTimespan.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.setDuration(3/5);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setDuration { |duration|
        var newStopOffset, newTimespan;

        duration = FoscDuration(duration);
        newStopOffset = this.startOffset + duration;

        newTimespan = this.copy;
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • setOffsets

    Sets timespan start offset to 'startOffset' and stop offset to 'stopOffset'.

    Returns a new FoscTimespan.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.setOffsets(stopOffset: 7/8);
    b.cs;

    Subtract negative 'startOffset' from existing stop offset.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.setOffsets(startOffset: -1/2);
    b.cs;

    Subtract negative 'stopOffset' from existing stop offset.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.setOffsets(stopOffset: -1/2);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    setOffsets { |startOffset, stopOffset|
        var newStartOffset, newStopOffset, newTimespan;

        if (startOffset.notNil) { startOffset = FoscOffset(startOffset) };
        if (stopOffset.notNil) { stopOffset = FoscOffset(stopOffset) };

        case
        { startOffset.notNil && { 0 <= startOffset } } {
            newStartOffset = startOffset;
        }
        { startOffset.notNil && { startOffset < 0 } } {
            newStartOffset = this.stopOffset + FoscOffset(startOffset);
        }
        {
            newStartOffset = this.startOffset;
        };

        case
        { stopOffset.notNil && { 0 <= stopOffset } } {
            newStopOffset = stopOffset;
        }
        { stopOffset.notNil && { stopOffset < 0 } } {
            newStopOffset = this.stopOffset + FoscOffset(stopOffset);
        }
        {
            newStopOffset = this.stopOffset;
        };

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', newStartOffset);
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • splitAtOffset

    Split into two parts when 'offset' happens during timespan.

    Returns a FoscTimespanList.

    code::
    a = FoscTimespan(0, 5);
    b = a.splitAtOffset(2);
    b.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    Returns a FoscTimespanList with a copy of receiver when 'offset' does not happen during timespan.

    code::
    a = FoscTimespan(0, 5);
    b = a.splitAtOffset(12);
    b.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    splitAtOffset { |offset|
        var result, left, right;

        offset = FoscOffset(offset);
        result = FoscTimespanList();

        if (this.startOffset < offset && { offset < this.stopOffset }) {
            left = this.species.new(this.startOffset, offset);
            right = this.species.new(offset, this.stopOffset);
            result = result.addAll([left, right]);
        } {
            result = result.add(this.copy);
        };

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • splitAtOffsets

    Split into one or more parts when 'offsets' happens during timespan.

    Returns a FoscTimespanList.

    code::
    a = FoscTimespan(0, 10);
    b = a.splitAtOffsets(#[1,3,7]);
    b.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''

    Returns a FoscTimespanList with a copy of receiver when 'offset' does not happen during timespan.

    code::
    a = FoscTimespan(0, 10);
    b = a.splitAtOffsets(#[-100]);
    b.do { |each| each.cs.postln };

    post::
    POSTOUTPUT
    '''
    '''
    -------------------------------------------------------------------------------------------------------- */
    splitAtOffsets { |offsets|
        var result, left, right;

        offsets = offsets.collect { |offset| FoscOffset(offset) };
        offsets = offsets.select { |offset| this.startOffset < offset && { offset < this.stopOffset } };
        offsets = offsets.as(Set).asArray.sort;
        result = FoscTimespanList();
        right = this.copy;

        offsets.do { |offset|
            # left, right = right.splitAtOffset(offset);
            result = result.add(left);
        };

        result = result.add(right);

        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsAfterOffset

    Is true when timespan overlaps start of 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.startsAfterOffset(-5);
    a.startsAfterOffset(0);
    a.startsAfterOffset(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsAfterOffset { |offset|
        offset = FoscOffset(offset);
        ^(offset < this.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsAfterTimespanStarts

    Is true when timespan starts after 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.startsAfterTimespanStarts(a);
    a.startsAfterTimespanStarts(b);
    b.startsAfterTimespanStarts(a);
    b.startsAfterTimespanStarts(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsAfterTimespanStarts { |timespan|
        ^(timespan.startOffset < this.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsAfterTimespanStops

    Is true when timespan stops after 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);
    c = FoscTimespan(10, 20);
    d = FoscTimespan(15, 25);

    code::
    a.startsAfterTimespanStops(a);
    b.startsAfterTimespanStops(a);
    c.startsAfterTimespanStops(a);
    d.startsAfterTimespanStops(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsAfterTimespanStops { |timespan|
        ^(timespan.stopOffset <= this.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsAtOffset

    Is true when timespan starts at 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.startsAtOffset(-5);
    a.startsAtOffset(0);
    a.startsAtOffset(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsAtOffset { |offset|
        ^(this.startOffset == offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsAtOrAfterOffset

    Is true when timespan starts at or after 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.startsAtOrAfterOffset(-5);
    a.startsAtOrAfterOffset(0);
    a.startsAtOrAfterOffset(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsAtOrAfterOffset { |offset|
        ^(this.startOffset >= offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsBeforeOffset

    Is true when timespan starts before 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.startsBeforeOffset(-5);
    a.startsBeforeOffset(0);
    a.startsBeforeOffset(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsBeforeOffset { |offset|
        ^(this.startOffset < offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsBeforeOrAtOffset

    Is true when timespan starts before or at 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.startsBeforeOrAtOffset(-5);
    a.startsBeforeOrAtOffset(0);
    a.startsBeforeOrAtOffset(5);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsBeforeOrAtOffset { |offset|
        ^(this.startOffset <= offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsBeforeTimespanStarts

    Is true when timespan starts before 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.startsBeforeTimespanStarts(a);
    a.startsBeforeTimespanStarts(b);
    b.startsBeforeTimespanStarts(a);
    b.startsBeforeTimespanStarts(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsBeforeTimespanStarts { |timespan|
        ^(this.startOffset < timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsBeforeTimespanStops

    Is true when timespan starts before 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.startsBeforeTimespanStops(a);
    a.startsBeforeTimespanStops(b);
    b.startsBeforeTimespanStops(a);
    b.startsBeforeTimespanStops(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsBeforeTimespanStops { |timespan|
        ^(this.startOffset < timespan.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsDuringTimespan

    Is true when timespan starts during 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.startsDuringTimespan(a);
    a.startsDuringTimespan(b);
    b.startsDuringTimespan(a);
    b.startsDuringTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsDuringTimespan { |timespan|
        ^(
            timespan.startOffset <= this.startOffset
            && (this.startOffset < timespan.stopOffset)
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsWhenTimespanStarts

    Is true when timespan starts when 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 15);

    code::
    a.startsWhenTimespanStarts(a);
    a.startsWhenTimespanStarts(b);
    b.startsWhenTimespanStarts(a);
    b.startsWhenTimespanStarts(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsWhenTimespanStarts { |timespan|
        ^(this.startOffset == timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • startsWhenTimespanStops

    Is true when timespan starts when 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.startsWhenTimespanStops(a);
    a.startsWhenTimespanStops(b);
    b.startsWhenTimespanStops(a);
    b.startsWhenTimespanStops(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    startsWhenTimespanStops { |timespan|
        ^(this.startOffset == timespan.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsAfterOffset

    Is true when timespan stops after 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.stopsAfterOffset(0);
    a.stopsAfterOffset(5);
    a.stopsAfterOffset(10);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsAfterOffset { |offset|
        offset = FoscOffset(offset);
        ^(offset < this.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsAfterTimespanStarts

    Is true when timespan stops after 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.stopsAfterTimespanStarts(a);
    a.stopsAfterTimespanStarts(b);
    b.stopsAfterTimespanStarts(a);
    b.stopsAfterTimespanStarts(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsAfterTimespanStarts { |timespan|
        ^(timespan.startOffset < this.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsAfterTimespanStops

    Is true when timespan stops after 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.stopsAfterTimespanStops(a);
    a.stopsAfterTimespanStops(b);
    b.stopsAfterTimespanStops(a);
    b.stopsAfterTimespanStops(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsAfterTimespanStops { |timespan|
        ^(timespan.stopOffset < this.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsAtOffset

    Is true when timespan stops at 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.stopsAtOffset(0);
    a.stopsAtOffset(10);
    a.stopsAtOffset(20);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsAtOffset { |offset|
        offset = FoscOffset(offset);
        ^(this.stopOffset == offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsAtOrAfterOffset

    Is true when timespan stops at or after 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.stopsAtOrAfterOffset(0);
    a.stopsAtOrAfterOffset(10);
    a.stopsAtOrAfterOffset(20);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsAtOrAfterOffset { |offset|
        offset = FoscOffset(offset);
        ^(offset <= this.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsBeforeOffset

    Is true when timespan stops before 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.stopsBeforeOffset(0);
    a.stopsBeforeOffset(10);
    a.stopsBeforeOffset(20);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsBeforeOffset { |offset|
        offset = FoscOffset(offset);
        ^(this.stopOffset < offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsBeforeOrAtOffset

    Is true when timespan stops before or at 'offset'.

    code::
    a = FoscTimespan(0, 10);

    code::
    a.stopsBeforeOrAtOffset(0);
    a.stopsBeforeOrAtOffset(10);
    a.stopsBeforeOrAtOffset(20);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsBeforeOrAtOffset { |offset|
        offset = FoscOffset(offset);
        ^(this.stopOffset <= offset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsBeforeTimespanStarts

    Is true when timespan stops before 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(15, 20);

    code::
    a.stopsBeforeTimespanStarts(a);
    a.stopsBeforeTimespanStarts(b);
    b.stopsBeforeTimespanStarts(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsBeforeTimespanStarts { |timespan|
        ^(this.stopOffset < timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsBeforeTimespanStops

    Is true when timespan stops before 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(15, 20);

    code::
    a.stopsBeforeTimespanStops(a);
    a.stopsBeforeTimespanStops(b);
    b.stopsBeforeTimespanStops(a);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsBeforeTimespanStops { |timespan|
        ^(this.stopOffset < timespan.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsDuringTimespan

    Is true when timespan stops during 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.stopsDuringTimespan(a);
    a.stopsDuringTimespan(b);
    b.stopsDuringTimespan(a);
    b.stopsDuringTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsDuringTimespan { |timespan|
        ^(
            timespan.startOffset < this.stopOffset
            && { this.stopOffset <= timespan.stopOffset }
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsWhenTimespanStarts

    Is true when timespan stops when 'timespan' starts.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.stopsWhenTimespanStarts(a);
    a.stopsWhenTimespanStarts(b);
    b.stopsWhenTimespanStarts(a);
    b.stopsWhenTimespanStarts(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsWhenTimespanStarts { |timespan|
        ^(this.stopOffset == timespan.startOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stopsWhenTimespanStops

    Is true when timespan stops when 'timespan' stops.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(10, 20);

    code::
    a.stopsWhenTimespanStops(a);
    a.stopsWhenTimespanStops(b);
    b.stopsWhenTimespanStops(a);
    b.stopsWhenTimespanStops(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    stopsWhenTimespanStops { |timespan|
        ^(this.stopOffset == timespan.stopOffset);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • stretch

    Stretches timespan by 'multiplier' relative to 'anchor'.

    Returns a new FoscTimespan.


    Stretch relative to timespan start offset.

    code::
    a = FoscTimespan(3, 10);
    b = a.stretch(2);
    b.cs;

    Stretch relative to timespan stop offset.

    code::
    a = FoscTimespan(3, 10);
    b = a.stretch(2, anchor: a.stopOffset);
    b.cs;

    Stretch relative to offset prior to timespan.

    code::
    a = FoscTimespan(3, 10);
    b = a.stretch(2, anchor: 0);
    b.cs;

    Stretch relative to offset after timespan.

    code::
    a = FoscTimespan(3, 10);
    b = a.stretch(3, anchor: 12);
    b.cs;

    Stretch relative to offset that happens during timespan.

    code::
    a = FoscTimespan(3, 10);
    b = a.stretch(2, anchor: 4);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    stretch { |multiplier, anchor|
        var newStartOffset, newStopOffset, newTimespan;

        multiplier = FoscMultiplier(multiplier);

        if (multiplier <= 0) {
            ^throw("%:roundOffsets: multiplier must be greater than 0: %."
                .format(this.species, multiplier.cs));
        };

        anchor = anchor ?? { this.startOffset };

        newStartOffset = (multiplier * (this.startOffset - anchor)) + anchor;
        newStopOffset = (multiplier * (this.stopOffset - anchor)) + anchor;

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', newStartOffset);
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • translate

    Translates timespan by 'translation'.

    Returns a new FoscTimespan.

    code::
    a = FoscTimespan(5, 10);
    b = a.translate(2);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    translate { |translation|
        ^this.translateOffsets(translation, translation);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • translateOffsets

    Translates timespan start offset by 'start_offset_translation' and stop offset by 'stop_offset_translation'.

    Returns a new FoscTimespan.

    code::
    a = FoscTimespan(1/2, 3/2);
    b = a.translateOffsets(startOffsetTranslation: -1/8);
    b.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    translateOffsets { |startOffsetTranslation, stopOffsetTranslation|
        var newStartOffset, newStopOffset, newTimespan;

        startOffsetTranslation = FoscDuration(startOffsetTranslation ? 0);
        stopOffsetTranslation = FoscDuration(stopOffsetTranslation ? 0);
        newStartOffset = this.startOffset + startOffsetTranslation;
        newStopOffset = this.stopOffset + stopOffsetTranslation;

        newTimespan = this.copy;
        newTimespan.instVarPut('startOffset', newStartOffset);
        newTimespan.instVarPut('stopOffset', newStopOffset);

        ^newTimespan;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • trisectsTimespan

    Is true when timespan trisects 'timespan'.

    code::
    a = FoscTimespan(0, 10);
    b = FoscTimespan(5, 6);

    code::
    a.trisectsTimespan(a);
    a.trisectsTimespan(b);
    b.trisectsTimespan(a);
    b.trisectsTimespan(b);
    '''
    -------------------------------------------------------------------------------------------------------- */
    trisectsTimespan { |timespan|
        ^(
            timespan.startOffset < this.startOffset
            && (this.stopOffset < timespan.stopOffset)
        );
    }
}
