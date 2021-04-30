/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: pianoPedal


SUMMARY:: Returns a pianoPedal.


DESCRIPTION:: Attaches piano pedal indicators.


USAGE::

'''
code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
set(a).pedalSustainStyle = 'mixed';
override(a).sustainPedalLineSpanner.staffPadding = 5;
a[0..].pianoPedal;
a.show;
'''

'''
code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].pianoPedal(type: 'sostenuto');
a.show;
'''

'''
code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].pianoPedal(type: 'corda');
a.show;
'''

'''
Piano pedals can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
set(a).pedalSustainStyle = 'mixed';
override(a).sustainPedalLineSpanner.staffPadding = 5;
a[0..].pianoPedal(tweaks: #[['color', 'blue']]);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    //!!!TODO: remove startPianoPedal and stopPianoPedal arguments, just use 'type'
    // NB: 'type' argument not in abjad
    pianoPedal { |startPianoPedal, stopPianoPedal, type='sustain', tag, tweaks|
        var leaves, startLeaf, stopLeaf;
        startPianoPedal = startPianoPedal ?? { FoscStartPianoPedal(type) };
        stopPianoPedal = stopPianoPedal ?? { FoscStopPianoPedal(type) };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;
        //!!! not in abjad
        if (startPianoPedal.tweaks.notNil) { tweaks = startPianoPedal.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(startPianoPedal, tweaks);
        //!!!
        startLeaf.attach(startPianoPedal, tag: tag);
        stopLeaf.attach(stopPianoPedal, tag: tag);
    }
}
