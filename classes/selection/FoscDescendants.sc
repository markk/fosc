/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscDescendants


SUMMARY:: Returns a FoscDescendants.


DESCRIPTION:: Descendants of a component.

Descendants is treated as the selection of the component's improper descendants.


USAGE::

'''
code::
a = FoscVoice([FoscNote(60, [1, 4])]);
b = FoscDescendants(a);
b.items.collect { |each| each };

code::
b.last;

code::
b.component;
'''
------------------------------------------------------------------------------------------------------------ */
FoscDescendants : FoscSequence {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <component, <components;
    *new { |component, crossOffset, includeSelf=true|
        var components, result, appendEach;
        if (component.isNil) {
            components = [];
        } {
            components = component.selectComponents;
        };
        result = [];
        if (crossOffset.isNil) {
            result = components;
        } {
            components.do { |component|
                appendEach = true;
                if (
                    (
                        component.prGetTimespan.startOffset < crossOffset
                        && { crossOffset < component.prGetTimespan.stopOffset }
                    ).not
                ) {
                    appendEach = false;
                };
                if (appendEach) { result = result.add(component) };
            };
        };
        ^super.new(result).initFoscDescendants(component);
    }
    initFoscDescendants { |argComponent|
        component = argComponent;
        components = items;
    }
}
