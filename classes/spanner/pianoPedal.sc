/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: pianoPedal


SUMMARY:: Returns a pianoPedal.


DESCRIPTION:: TODO


USAGE::

'''

• pianoPedal (abjad 3.0)

Attaches piano pedal indicators.


• Example 1

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
set(a).pedalSustainStyle = 'mixed';
override(a).sustainPedalLineSpanner.staffPadding = 5;
a[0..].pianoPedal;
a.show;

img:: ![](../img/spanner-piano-pedal-1.png)
'''

p = "%/fosc/docs/img/spanner-piano-pedal-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].pianoPedal(type: 'sostenuto');
a.show;

img:: ![](../img/spanner-piano-pedal-2.png)
'''

p = "%/fosc/docs/img/spanner-piano-pedal-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].pianoPedal(type: 'corda');
a.show;

img:: ![](../img/spanner-piano-pedal-3.png)
'''

p = "%/fosc/docs/img/spanner-piano-pedal-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 4

Piano pedals can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
set(a).pedalSustainStyle = 'mixed';
override(a).sustainPedalLineSpanner.staffPadding = 5;
a[0..].pianoPedal(tweaks: #[['color', 'blue']]);
a.show;

img:: ![](../img/spanner-piano-pedal-4.png)
'''

p = "%/fosc/docs/img/spanner-piano-pedal-4".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



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
