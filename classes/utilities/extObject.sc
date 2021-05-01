/* ------------------------------------------------------------------------------------------------------------
TITLE:: extObject


SUMMARY:: Returns a extObject.


DESCRIPTION:: Extensions to Object


USAGE::
------------------------------------------------------------------------------------------------------------ */
+ Object {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • assert

    '''
    code::nointerpret
    assert(false);

    post::
    ERROR: Object:assert
    '''

    '''
    FIXME: ERROR: Class not defined.

    code::nointerpret
    FoscSegmentMaker(annotateSegments: true);   // fine - correct type
    FoscSegmentMaker(annotateSegments: 2);      // not fine - incorrect type
    FoscSegmentMaker(meterSpecifier: 'foo');    // not fine - incorrect type
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
    • isBoolean
    -------------------------------------------------------------------------------------------------------- */
    isBoolean {
        ^this.isKindOf(Boolean);
    }
    /* --------------------------------------------------------------------------------------------------------
    • ccs

    Compact compile string.

    '''
    code::
    [1, 2, 3, 4, 5].ccs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ccs {
        ^this.cs.removeWhiteSpace;
    }
}
