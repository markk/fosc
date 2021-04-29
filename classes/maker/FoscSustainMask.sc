/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscSustainMask


SUMMARY:: Returns a FoscSustainMask.


DESCRIPTION:: Sustain mask.

FIXME: none of these examples work

USAGE::

'''
Rhythm-maker.

code::
p = FoscPattern(#[0,1,4,5]) | FoscPattern.last(3);
m = FoscSustainMask(p);
a = FoscRhythmMaker();
m = a.(divisions: 1/4 ! 4, ratios: #[[1,1,1,1,1]], masks: [m]);
a.show;
nointerpret
'''

'''
Leaf-maker

code::
a = FoscLeafMaker().((60..75), [1/8]);
p = FoscPattern(#[0,1,4,5]) | FoscPattern.last(3);
m = FoscSustainMask(p).([a]);
FoscSelection(m).show;
nointerpret
'''

'''
Fuse contiguous leaves when 'fuse' is true.

code::
a = FoscLeafMaker().((60..75), [1/8]);
p = FoscPattern(#[0,1,4,5]) | FoscPattern.last(3);
m = FoscSustainMask(p, fuse: true).([a]);
FoscSelection(m).show;
nointerpret
'''

'''
Rewrite meter for previous example.

code::
m = FoscMeterSpecifier(meters: #[[4,4],[4,4]], boundaryDepth: 1).(m);
FoscSelection(m).show;
nointerpret
'''

'''
Fuse contiguous leaves when 'fuse' is true.

code::
p = FoscPattern(#[0,1,4,5]) | FoscPattern.last(3);
m = FoscSustainMask(p, fuse: true);
a = FoscRhythmMaker();
m = a.(divisions: 1/4 ! 4, ratios: #[[1,1,1,1,1]], masks: [m]);
FoscSelection(m).show;
nointerpret
'''

'''
Apply tuplet specifier to previous example.

code::
m = FoscTupletSpecifier(extractTrivial: true, rewriteRestFilled: true, rewriteSustained: true).(m); FoscSelection(m).show;
nointerpret
'''

'''
Create a talea pattern.

code::
p = FoscPattern(#[0,1,3], period: 6);
m = FoscSustainMask(p, fuse: true);
a = FoscRhythmMaker();
a.(divisions: 1/4 ! 4, ratios: #[[1,1,1,1,1]], masks: [m]);
a.show;
nointerpret
'''

'''
code::
m = FoscSustainMask(FoscPattern(indices: #[0,1,4,5,17,18,19]), fuse: true);
a = FoscRhythmMaker();
a.(divisions: 1/4 ! 4, ratios: #[[1,1,1,1,1]], masks: [m]);
a.show;
nointerpret
'''
------------------------------------------------------------------------------------------------------------ */
FoscSustainMask : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <pattern, <fuse;
    *new { |pattern, fuse=false|
        if (pattern.isKindOf(FoscSustainMask)) { pattern = pattern.pattern };
        if (pattern.isKindOf(FoscSegmentList)) { pattern = pattern.asFoscPattern };

        assert(pattern.isKindOf(FoscPattern), thisMethod, 'pattern', pattern);
        assert(fuse.isKindOf(Boolean), thisMethod, 'fuse', fuse);

        ^super.new.init(pattern, fuse);
    }
    init { |argPattern, argFuse|
        pattern = argPattern;
        fuse = argFuse;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • value
    -------------------------------------------------------------------------------------------------------- */
    value { |selections|
        var newSelections, containers, container, logicalTies, totalLogicalTies, matchingLogicalTies, indices;
        var sizes, rest, newSelection;

        newSelections = [];
        containers = [];

        selections.do { |selection|
            container = FoscContainer();
            container.addAll(selection);
            containers = containers.add(container);
        };

        logicalTies = all(FoscIteration(selections).logicalTies);
        totalLogicalTies = logicalTies.size;
        matchingLogicalTies = pattern.getMatchingItems(logicalTies);

        if (fuse) {
            indices = matchingLogicalTies.collect { |each| logicalTies.indexOf(each) };
            indices = indices.add(totalLogicalTies);
            sizes = indices.intervals;
            logicalTies.clumps(sizes).do { |each| FoscSelection(each).prFuseLeaves };
        } {
            pattern.copy.invert.getMatchingItems(logicalTies).do { |logicalTie|
                if (logicalTie.head.isKindOf(FoscRest).not) {
                    logicalTie.do { |leaf|
                        rest = FoscRest(leaf.writtenDuration);
                        if (leaf.multiplier.notNil) { rest.multiplier_(leaf.multiplier) };
                        mutate(leaf).replace([rest]);
                        rest.detach(FoscTie);
                    };
                };
            };
        };

        containers.do { |container|
            newSelection = mutate(container).ejectContents;
            newSelections = newSelections.add(newSelection);
        };

        ^newSelections;
    }
}
