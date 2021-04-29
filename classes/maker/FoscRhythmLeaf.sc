/* ------------------------------------------------------------------------------------------------------------
TITLE:: FoscRhythmLeaf


SUMMARY:: Returns a FoscRhythmLeaf.


DESCRIPTION:: A rhythm-tree leaf.


USAGE::

'''
code::
a = FoscRhythmLeaf(5);
b = a.(1/16);
b.duration.str;

code::
FoscVoice(b).show;
'''

'''
code::
a = FoscRhythmLeaf(-5);
b = a.(1/16);
b.duration.str;

code::
FoscVoice(b).show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscRhythmLeaf : FoscTreeNode {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <preProlatedDuration, <isPitched, <offset, <offsetsAreCurrent=false;
    // DEPRECATE: var <isTiedToPrevLeaf=false;
    var mixin;
    *new { |preProlatedDuration|
        if (preProlatedDuration.isKindOf(FoscRhythmLeaf)) { ^preProlatedDuration };
        ^super.new.initFoscRhythmLeaf(preProlatedDuration);
    }
    initFoscRhythmLeaf { |argPreProlatedDuration|
        preProlatedDuration = FoscDuration(argPreProlatedDuration.abs);
        isPitched = (argPreProlatedDuration > 0);
        mixin = FoscRhythmMixin();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • doesNotUnderstand

    Delegate unknown methods to FoscRhythmMixin.
    -------------------------------------------------------------------------------------------------------- */
    doesNotUnderstand { |selector ... args|
        if (mixin.respondsTo(selector)) {
            ^mixin.performList(selector, [this].addAll(args));
        } {
            throw(DoesNotUnderstandError(this, selector, args));
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    • value

    Generate Abjad score components.

    Returns selection.

    '''
    code::
    a = FoscRhythmLeaf(5);
    a.prGetPreprolatedDuration.str;
    b = a.([1, 16]);
    FoscVoice(b).show;
    '''

    '''
    code::
    a = FoscNonreducedFraction(#[2, 4]);
    a = a * 3;
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    value { |pulseDuration|
       var totalDuration, maker;
        pulseDuration = FoscDuration(pulseDuration);
        totalDuration = pulseDuration * this.prGetPreprolatedDuration;
        if (this.isPitched) { ^FoscLeafMaker().([60], [totalDuration]) };
        ^FoscLeafMaker().([nil], [totalDuration]);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    prGetPreprolatedDuration {
        ^preProlatedDuration;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • duration

    '''
    code::
    a = FoscRhythm(3/4, [1, 2, 2, 1, 1]);
    a.duration.str;
    '''

    '''
    code::
    b = FoscRhythm(4, [-3, 2]);
    c = FoscRhythm(3/4, [1, 2, b]);
    c.duration.str;

    code::
    b.duration.str;

    code::
    b.items.last.duration.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • improperParentage

    '''
    code::
    b = FoscRhythm(4, [-3, 2]);
    a = FoscRhythm(3/4, [1, 2, b]);

    a.improperParentage;

    code::
    b.improperParentage;

    code::
    b.items.last.improperParentage;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parentageRatios

    A sequence describing the relative durations of the nodes in a node's improper parentage.

    The first item in the sequence is the preprolated_duration of the root node,
    and subsequent items are pairs of the preprolated duration of the next node
    in the parentage and the total preprolated_duration of that node and its
    siblings.

    Returns array.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • prolation

    Prolation of rhythm tree node.

    Returns multiplier.

    '''
    code::
    b = FoscRhythm(4, [-3, 2]);
    a = FoscRhythm([3, 4], [1, 2, b]);
    a.prolation.pair;

    code::
    b.prolation.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • prolations

    Prolations of rhythm tree node.

    Returns array.

    '''
    code::
    b = FoscRhythm(4, [-3, 2]);
    a = FoscRhythm([3, 4], [1, 2, b]);
    a.prolations.do { |each| each.pair.postln };  // FIXME
    b.prolations.do { |each| each.pair.postln };
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • properParentage

    '''
    code::
    b = FoscRhythm(4, [-3, 2]);
    a = FoscRhythm([3, 4], [1, 2, b]);
    a.properParentage;
    b.properParentage;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • startOffset

    The starting offset of a node in a rhythm-tree relative to the root.

    Returns a FoscOffset.

    '''
    code::
    a = FoscRhythm([1, 1], [1, [1, [1, 1]], [1, [1, 1]]]);
    a.do { |node|
        node.depth.do { Post.tab };
        node.startOffset.pair.postln;
    };
    nointerpret

    post::
    [ 0, 1 ]
      [ 0, 1 ]
      [ 1, 3 ]
        [ 1, 3 ]
        [ 1, 2 ]
      [ 2, 3 ]
        [ 2, 3 ]
        [ 5, 6 ]
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • stopOffset

    The stopping offset of a node in a rhythm-tree relative to the root.

    Returns a FoscOffset.

    '''
    code::
    a = FoscRhythm(1, [1, [1, [1, 1]], [1, [1, 1]]]);
    a.do { |node|
        node.depth.do { Post.tab };
        node.stopOffset.pair.postln
    };
    nointerpret

    post::
    [ 1, 1 ]
      [ 1, 3 ]
      [ 2, 3 ]
        [ 1, 2 ]
        [ 2, 3 ]
      [ 1, 1 ]
        [ 5, 6 ]
        [ 1, 1 ]
    '''
    -------------------------------------------------------------------------------------------------------- */


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DEPRECATE
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • isTiedToPrevLeaf_
    -------------------------------------------------------------------------------------------------------- */
    // isTiedToPrevLeaf_ { |bool|
    //     isTiedToPrevLeaf = bool;
    // }
}
