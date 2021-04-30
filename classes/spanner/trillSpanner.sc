/* --------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: trillSpanner


SUMMARY:: Returns a trillSpanner.


DESCRIPTION:: Trill spanner.


USAGE::

'''
Attached unpitched trill spanner to all notes in staff.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/8));
a[0..].trillSpanner;
a.show;
'''

'''
Attached pitched trill spanner to all notes in staff.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/8));
a[0..].trillSpanner(pitch: "C#4");
a.show;
'''

'''
Attached pitched trill spanner to all notes in staff.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/8));
a[0..].trillSpanner(interval: 1);
a.show;
'''

'''
Pitched trill spanner must appear after markup to avoid hiding markup in output.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/8));
n = FoscMarkup('Allegro', direction: 'up');
a[0].attach(n);
a[0..].trillSpanner;
a.show;
'''
-------------------------------------------------------------------------------------------------------- */
+ FoscSelection {
    //!!!TODO: pitch and interval args not in abjad
    trillSpanner { |startTrillSpan, stopTrillSpan, pitch, interval, tag, tweaks|
        var leaves, startLeaf, stopLeaf;
        startTrillSpan = startTrillSpan ?? { FoscStartTrillSpan(pitch: pitch, interval: interval) };
        stopTrillSpan = stopTrillSpan ?? { FoscStopTrillSpan() };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;
        //!!! not in abjad
        if (startTrillSpan.tweaks.notNil) { tweaks = startTrillSpan.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(startTrillSpan, tweaks);
        //!!!
        startLeaf.attach(startTrillSpan, tag: tag);
        stopLeaf.attach(stopTrillSpan, tag: tag);
    }
}
