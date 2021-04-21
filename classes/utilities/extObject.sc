/* ------------------------------------------------------------------------------------------------------------
TITLE:: extObject


SUMMARY:: Returns a extObject.


DESCRIPTION:: TODO


USAGE::

'''

• Object
'''
------------------------------------------------------------------------------------------------------------ */
+ Object {
    /* --------------------------------------------------------------------------------------------------------
    '''
    • assert

    code::
    assert(false);
    FoscSegmentMaker(annotateSegments: true);   // fine - correct type
    FoscSegmentMaker(annotateSegments: 2);      // not fine - incorrect type
    FoscSegmentMaker(meterSpecifier: 'foo');        // not fine - incorrect type
    '''
    -------------------------------------------------------------------------------------------------------- */
    assert { |method, argName, val|
        var bool;
        
        bool = try { this.asBoolean } { false };
        if (bool) { ^nil };

        if (method.isNil) {
            ^MethodError(thisMethod, this).throw;
        } {
            ^FoscValueError(method, argName, val).throw;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isBoolean
    '''
    -------------------------------------------------------------------------------------------------------- */
    isBoolean {
        ^this.isKindOf(Boolean);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • ccs

    Compact compile string.

    code::
    [1, 2, 3, 4, 5].ccs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ccs {
        ^this.cs.removeWhiteSpace;
    }
}
