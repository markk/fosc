/* ------------------------------------------------------------------------------------------------------------
TITLE:: FoscIteration


SUMMARY:: Returns a FoscIteration.


DESCRIPTION:: Iterates objects


USAGE::

'''
Iterates notes.

code::
a = FoscLeafMaker().(#[60,62,64,65,67,69], [1/8]);
b = [];
FoscIteration(a).components.do { |each| b = b.add(each.cs) };
b.join("\n");
'''
------------------------------------------------------------------------------------------------------------ */
FoscIteration : FoscStream {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <client;
    *new { |client|
        assert(
            [FoscComponent, FoscSelection, SequenceableCollection].any { |type| client.isKindOf(type) },
            "FoscIteration:new: client must be a FoscComponent, FoscSelection, or SequenceableCollection: %."
                .format(client);
        );
        ^super.new.init(client);
    }
    init { |argClient|
        client = argClient;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • client

    Gets client.

    Returns component or selection.

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    FoscIteration(a).client;
    '''

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    FoscIteration(a[0..]).client;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SPECIAL PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        //!!!TODO
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • components

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69], [1/8]));
    b = [];
    FoscIteration(a).components.do { |each| b = b.add(each.cs) };
    b.join("\n");
    '''

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69], [1/8]));
    b = [];
    FoscIteration(a).components(prototype: FoscNote).do { |each| b = b.add(each.cs) };
    b.join("\n");
    '''

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69], [1/8]));
    b = [];
    FoscIteration(a).components(FoscNote, reverse: true).do { |each| b = b.add(each.cs) };
    b.join("\n");
    '''
    -------------------------------------------------------------------------------------------------------- */
    components { |prototype, exclude, doNotIterateGraceContainers=false, graceNotes=false, reverse=false|
        var localClient, iterablePrototype, graceContainer, afterGraceContainer, embeddedRoutine;

        localClient = client;
        prototype = prototype ? FoscComponent;
        if (prototype.isSequenceableCollection.not) { prototype = [prototype] };
        exclude = FoscIteration.prCoerceExclude(exclude);
        assert(exclude.isSequenceableCollection);
        iterablePrototype = [FoscContainer, FoscSelection, SequenceableCollection];

        if (graceNotes && localClient.isKindOf(FoscLeaf)) {
            graceContainer = localClient.graceContainer;
            afterGraceContainer = localClient.afterGraceContainer;
        };

        routine = Routine {
            if (reverse.not) {
                if (doNotIterateGraceContainers.not && graceNotes && graceContainer.notNil) {
                    embeddedRoutine = FoscIteration(graceContainer).components(
                        prototype,
                        doNotIterateGraceContainers: doNotIterateGraceContainers,
                        graceNotes: graceNotes,
                        reverse: reverse
                    );
                    embeddedRoutine.next.yield;
                    //if (next.notNil) { next.yield };
                };
                if (prototype.any { |type| localClient.isKindOf(type) }) {
                    if (
                        (graceNotes && FoscInspection(localClient).graceNote)
                        || { graceNotes.not && FoscInspection(localClient).graceNote.not }

                    ) {
                        if (FoscIteration.prShouldExclude(localClient, exclude).not) {
                            localClient.yield;
                        };
                    };
                };
                if (doNotIterateGraceContainers.not && graceNotes && afterGraceContainer.notNil) {
                    embeddedRoutine = FoscIteration(afterGraceContainer).components(
                        prototype,
                        exclude: exclude,
                        doNotIterateGraceContainers: doNotIterateGraceContainers,
                        graceNotes: graceNotes,
                        reverse: reverse
                    );
                    embeddedRoutine.next.yield;
                    //if (next.notNil) { next.yield };
                };
                if (iterablePrototype.any { |type| localClient.isKindOf(type) }) {
                    localClient.do { |each|
                        embeddedRoutine = FoscIteration(each).components(
                            prototype: prototype,
                            exclude: exclude,
                            doNotIterateGraceContainers: doNotIterateGraceContainers,
                            graceNotes: graceNotes,
                            reverse: reverse
                        );
                        //embeddedRoutine = embeddedRoutine.routine;
                        embeddedRoutine.routine.embedInStream;
                    };
                };
            } {
                if (doNotIterateGraceContainers.not && graceNotes && afterGraceContainer.notNil) {
                    embeddedRoutine = FoscIteration(afterGraceContainer).components(
                        prototype,
                        exclude: exclude,
                        doNotIterateGraceContainers: doNotIterateGraceContainers,
                        graceNotes: graceNotes,
                        reverse: reverse
                    );
                    embeddedRoutine.next.yield;
                    //if (next.notNil) { next.yield };
                };
                if (prototype.any { |type| localClient.isKindOf(type) }) {
                    if (
                        (graceNotes && FoscInspection(localClient).graceNote)
                        || { graceNotes.not && FoscInspection(localClient).graceNote.not }

                    ) {
                        if (FoscIteration.prShouldExclude(localClient, exclude).not) {
                            localClient.yield;
                        };
                    };
                };
                if (doNotIterateGraceContainers.not && graceNotes && graceContainer.notNil) {
                    embeddedRoutine = FoscIteration(graceContainer).components(
                        prototype,
                        doNotIterateGraceContainers: doNotIterateGraceContainers,
                        graceNotes: graceNotes,
                        reverse: reverse
                    );
                    embeddedRoutine.next.yield;
                    //if (next.notNil) { next.yield };
                };
                if (iterablePrototype.any { |type| localClient.isKindOf(type) }) {
                    localClient.reverseDo { |each|
                        embeddedRoutine = FoscIteration(each).components(
                            prototype: prototype,
                            exclude: exclude,
                            doNotIterateGraceContainers: doNotIterateGraceContainers,
                            graceNotes: graceNotes,
                            reverse: reverse
                        );
                        //embeddedRoutine = embeddedRoutine.routine;
                        embeddedRoutine.routine.embedInStream;
                    };
                };
            };
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    • leafPairs

    '''
    !!!TODO: is this method needed? can just do:

    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,67,69], [1/8]));
    o = [];
    FoscIteration(a).leaves.doAdjacentPairs { |a, b| o = o.add([a.str, b.str]) };
    o.join("\n");
    '''
    -------------------------------------------------------------------------------------------------------- */
    leafPairs {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • leaves

    '''
    iterate all leaves

    code::
    a = FoscLeafMaker().(#[60,62,nil,65,67,nil], [1/8]);
    o = [];
    FoscIteration(a).leaves.do { |each| o = o.add(each.cs) };
    o.join("\n");
    '''

    '''
    iterate pitched leaves

    code::
    a = FoscLeafMaker().(#[60,62,nil,65,67,nil], [1/8]);
    o = [];
    FoscIteration(a).leaves(pitched: true).do { |each| o = o.add(each.cs) };
    o.join("\n");
    '''


    '''
    iterate non-pitched leaves

    code::
    a = FoscLeafMaker().(#[60,62,nil,65,67,nil], [1/8]);
    o = [];
    FoscIteration(a).leaves(pitched: false).do { |each| o = o.add(each.cs) };
    o.join("\n");
    '''

    '''
    iterate all leaf pairs

    code::
    a = FoscLeafMaker().(#[60,62,64,65,67], [1/8]);
    o = [];
    FoscIteration(a).leaves.doAdjacentPairs { |a, b| o = o.add([a.cs, b.cs]) };
    o.join("\n");
    '''
    -------------------------------------------------------------------------------------------------------- */
    leaves { |prototype, exclude, doNotIterateGraceContainers=false, graceNotes=false, pitched,
        reverse=false|
        prototype = prototype ? FoscLeaf;
        case
        { pitched == true } {
            prototype = [FoscChord, FoscNote];
        }
        { pitched == false } {
            prototype = [FoscMultimeasureRest, FoscRest, FoscSkip];
        };
        ^this.components(
            prototype: prototype,
            exclude: exclude,
            doNotIterateGraceContainers: doNotIterateGraceContainers,
            graceNotes: graceNotes,
            reverse: reverse
        );
    }
    /* --------------------------------------------------------------------------------------------------------
    • logicalTies

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,60,62,nil,64,64], [1/4,1/24,1/12,1/8,1/4,1/4]));
    m = a.selectLeaves;
    tie(m[0..1]);
    tie(m[4..]);
    a.show;
    '''

    '''
    iterate all logicalTies

    code::
    b = FoscIteration(a).logicalTies;
    o = [];
    b.all.do { |each| o = o.add(each.items.collect { |each| each.cs } ) };
    o.join("\n");
    '''

    '''
    iterate pitched logicalTies

    FIXME output: `ERROR: Message 'items' not understood.`

    code::nointerpret
    b = FoscIteration(a).logicalTies(pitched: true);
    o = [];
    b.do { |each| o = o.add(each.items.collect { |each| each.cs } ) };
    o.join("\n");
    '''

    '''
    iterate non-pitched logicalTies

    code::
    b = FoscIteration(a).logicalTies(pitched: false);
    o = [];
    b.do { |each| o = o.add(each.items.collect { |each| each.cs } ) };
    o.join("\n");
    '''

    '''
    iterate nontrivial logicalTies

    FIXME output: `ERROR: Message 'items' not understood.`

    code::nointerpret
    b = FoscIteration(a).logicalTies(nontrivial: true);
    b.do { |each| each.items.collect { |each| each.cs }.postln };
    '''

    '''
    iterate trivial logicalTies

    FIXME output: `ERROR: Message 'items' not understood.`

    code::nointerpret
    b = FoscIteration(a).logicalTies(nontrivial: false);
    b.do { |each| each.items.collect { |each| each.cs }.postln };
    '''

    '''
    iterate logicalTies in reverse order

    FIXME output: `ERROR: Message 'items' not understood.`

    code::nointerpret
    b = FoscIteration(a).logicalTies(reverse: true);
    b.do { |each| each.items.collect { |each| each.cs }.postln };
    '''
    -------------------------------------------------------------------------------------------------------- */
    logicalTies { |exclude, graceNotes=false, nontrivial, pitched, reverse=false|
        var yieldedLogicalTies, logicalTie;

        yieldedLogicalTies = Set[];

        routine = Routine {
            this.leaves(
                exclude: exclude,
                graceNotes: graceNotes,
                pitched: pitched,
                reverse: reverse
            ).do { |leaf|
                logicalTie = leaf.prGetLogicalTie;
                if (leaf == logicalTie.head) {
                    if (
                        nontrivial.isNil
                        || { nontrivial && { logicalTie.isTrivial.not } }
                        || { nontrivial.not && { logicalTie.isTrivial } }
                    ) {
                        if (yieldedLogicalTies.includes(logicalTie).not) {
                            yieldedLogicalTies.add(logicalTie);
                            logicalTie.yield;
                        };
                    };
                };
            };
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    • outOfRange
    -------------------------------------------------------------------------------------------------------- */
    outOfRange {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • pitchPairs
    -------------------------------------------------------------------------------------------------------- */
    pitchPairs {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • pitches

    '''
    Iterate pitches in selection.

    code::
    a = FoscLeafMaker().(#[60,62,64,[61,63,65]], [1/8]);
    o = [];
    FoscIteration(a).pitches.do { |each| o = o.add(each.str) };
    o.join("\n");
    '''

    '''
    Iterate pitches in container.

    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,[61,63,65]], [1/8]));
    o = [];
    FoscIteration(a).pitches.do { |each| o = o.add(each.str) };
    o.join("\n");
    '''


    '''
    !!!TODO: not supported in fosc
    Iterate pitches in FoscPitchSet.

    code::nointerpret
    a = FoscPitchSet(#[60,62,64,65]);
    o = [];
    FoscIteration(a).pitches.do { |each| o = o.add(each.str) };
    o.join("\n");
    '''

    '''
    !!!TODO: not supported in fosc
    Iterate various types of object.

    code::nointerpret
    a = [FoscPitch("C#4"), FoscNote(72, 1/4), FoscChord(#[60,64,67], 1/4)];
    o = [];
    FoscIteration(a).pitches.do { |each| o = o.add(each.str) };
    o.join("\n");
    '''
    -------------------------------------------------------------------------------------------------------- */
    pitches {
        var result;
        result = [];
        FoscIteration(client).leaves(pitched: true).do { |leaf|
            case
            { leaf.isKindOf(FoscNote) } {
                result = result.add(leaf.writtenPitch);
            }
            { leaf.isKindOf(FoscChord) } {
                result = result.addAll(leaf.writtenPitches.items);
            };
        };
        routine = iter(result);
    }
    // pitches {
    //     var result;

    //     routine = Routine {};
    //     result = [];

    //     if (client.isKindOf(FoscPitch)) {
    //         routine = routine ++ Routine { client.yield };
    //     };

    //     try { result = result.addAll(client.pitches) };

    //     case
    //     { client.isKindOf(FoscChord) } {
    //         result = result.addAll(client.writtenPitches.items);
    //     }
    //     { client.isKindOf(FoscPitchSegment) } {
    //         result = result.addAll(client.items);
    //     }
    //     { client.isKindOf(FoscPitchSet) } {
    //         result = result.addAll(client.items.sort);
    //     }
    //     { client.isSequenceableCollection } {
    //         client.do { |each|
    //             FoscIteration(each).pitches.do { |pitch| result = result.add(pitch) };
    //         }
    //     }
    //     {
    //         FoscIteration(client).leaves.do { |leaf|
    //             try { result = result.add(leaf.writtenPitch) };
    //             try { result = result.addAll(leaf.writtenPitches.items) };
    //         }
    //     };

    //     routine = routine ++ iter(result);
    // }
    /* --------------------------------------------------------------------------------------------------------
    • runs

    '''
    '''
    .separate { |a, b| a.isPitched != b.isPitched && { a.stopOffset == b.startOffset }}
    -------------------------------------------------------------------------------------------------------- */
    // runs {
    // }
    /* --------------------------------------------------------------------------------------------------------
    • timeline

    !!!TODO: 'protoype' can not currently be FoscLogicalTie


    '''
    iterate all leaves

    code::
    a = FoscStaff(FoscLeafMaker().((60..67), [1/8]));
    b = FoscStaff(FoscLeafMaker().((60..63), [1/4]));
    c = FoscScore([a, b]);
    FoscIteration(c).timeline.do { |each, i| each.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''


    '''
    iterate all leaves in reverse

    code::
    a = FoscStaff(FoscLeafMaker().((60..67), [1/8]));
    b = FoscStaff(FoscLeafMaker().((60..63), [1/4]));
    c = FoscScore([a, b]);
    FoscIteration(c).timeline(reverse: true).do { |each, i| each.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''


    '''
    iterate pitched leaves

    code::
    a = FoscStaff(FoscLeafMaker().([60,61,nil,63,nil,nil,65], [1/8]));
    b = FoscStaff(FoscLeafMaker().((60..63), [1/4]));
    c = FoscScore([a, b]);
    FoscIteration(c).timeline(pitched: true).do { |each, i| each.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''


    '''
    iterate non-pitched leaves

    code::
    a = FoscStaff(FoscLeafMaker().(#[60,61,nil,63,nil,nil,65], [1/8]));
    b = FoscStaff(FoscLeafMaker().((60..63), [1/4]));
    c = FoscScore([a, b]);
    FoscIteration(c).timeline(pitched: false).do { |each, i| each.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    timeline { |prototype, exclude, pitched, reverse=false|
        var components, indices, scoreIndex;
        components = all(this.leaves(prototype: prototype, exclude: exclude, pitched: pitched));
        indices = components.collect { |each|
            //!!!TODO: graceNotes ??
            scoreIndex = each.prGetParentage(graceNotes: true).scoreIndex;
            [each.prGetTimespan.startOffset].addAll(scoreIndex);
        };
        components = components[indices.orderN];
        if (reverse) { components = components.reverse };
        routine = iter(components);
    }
    /* --------------------------------------------------------------------------------------------------------
    • timelineByLogicalTies

    '''
    iterate logical ties

    code::
    a = FoscStaff(FoscLeafMaker().((60..67), [5/32]));
    b = FoscStaff(FoscLeafMaker().((60..63), [5/16]));
    c = FoscScore([a, b]);
    FoscIteration(c).timelineByLogicalTies.do { |each, i| each.head.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''

    '''
    iterate pitched logical ties

    code::
    a = FoscStaff(FoscLeafMaker().(#[60,61,nil,63,nil,nil,65], [5/32]));
    b = FoscStaff(FoscLeafMaker().((60..63), [5/16]));
    c = FoscScore([a, b]);
    FoscIteration(c).timelineByLogicalTies(pitched: true)
        .do { |each, i| each.head.attach(FoscMarkup(i, 'above')) };
    c.show;
    '''
    -------------------------------------------------------------------------------------------------------- */
    timelineByLogicalTies { |exclude, pitched, reverse=false|
        var logicalTies, indices, scoreIndex;
        logicalTies = all(this.logicalTies(exclude: exclude, pitched: pitched));
        indices = logicalTies.collect { |each|
            //!!!TODO: graceNotes ??
            scoreIndex = each.head.prGetParentage(graceNotes: true).scoreIndex;
            [each.head.prGetTimespan.startOffset].addAll(scoreIndex);
        };
        logicalTies = logicalTies[indices.orderN];
        if (reverse) { logicalTies = logicalTies.reverse };
        routine = iter(logicalTies);
    }
    /* --------------------------------------------------------------------------------------------------------
    • verticalMoments
    -------------------------------------------------------------------------------------------------------- */
    verticalMoments { |reverse=false|
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *prCoerceExclude
    -------------------------------------------------------------------------------------------------------- */
    *prCoerceExclude { |exclude|
        if (exclude.isNil) { ^[] } { ^[exclude] };
    }
    /* --------------------------------------------------------------------------------------------------------
    • *prShouldExclude
    -------------------------------------------------------------------------------------------------------- */
    *prShouldExclude { |object, exclude|
        assert(exclude.isSequenceableCollection);
        exclude.do { |symbol|
            //!!!TODO: if (FoscInspection(object).annotation(symbol)) { ^true };
        };
        ^false;
    }
}
