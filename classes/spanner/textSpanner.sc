/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: textSpanner


SUMMARY:: Returns a textSpanner.


DESCRIPTION:: TODO


USAGE::

'''

• textSpanner (abjad 3.0)

Attaches text span indicators.


• Example 1

Attaches text span indicators.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/4));
x = FoscStartTextSpan(
    leftText: FoscMarkup('pont.').upright,
    rightText: FoscMarkup('tasto').upright,
    style: 'solidLineWithArrow'
code::
);
a[0..].textSpanner(x);
a.show;

img:: ![](../img/spanner-text-spanner-1.png)
'''

p = "%/fosc/docs/img/spanner-text-spanner-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Strings are automatically wrapped as markups.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], 1/4));
x = FoscStartTextSpan("pont.", "tasto", 'solidLineWithArrow');
a[0..].textSpanner(x);
override(a).textSpanner.staffPadding = 4;
a.show;

img:: ![](../img/spanner-text-spanner-2.png)
'''

p = "%/fosc/docs/img/spanner-text-spanner-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Enchained spanners.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65,nil], 2/4));
x = FoscStartTextSpan("pont.", style: 'solidLineWithArrow');
a[..2].textSpanner(x);
x = FoscStartTextSpan("tasto", "pont.", 'solidLineWithArrow');
a[2..].textSpanner(x);
override(a).textSpanner.staffPadding = 4;
a.show;

img:: ![](../img/spanner-text-spanner-3.png)
'''

p = "%/fosc/docs/img/spanner-text-spanner-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));




code::
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    textSpanner { |startTextSpan, stopTextSpan|
        var leaves, startLeaf, stopLeaf;
        startTextSpan = startTextSpan ?? { FoscStartTextSpan() };
        stopTextSpan = stopTextSpan ?? { FoscStopTextSpan() };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;
        startLeaf.attach(startTextSpan);
        stopLeaf.attach(stopTextSpan);
    }
}
