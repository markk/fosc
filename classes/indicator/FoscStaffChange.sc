/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStaffChange


SUMMARY:: Returns a FoscStaffChange.


DESCRIPTION:: TODO


USAGE::

'''

• FoscStaffChange (abjad 3.0)

Staff change.


• Example 1

code::
g = FoscStaffGroup();
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]), name: 'RH');
b = FoscStaff([FoscSkip(2/4)], name: 'LH');
g.addAll([a, b]);
m = FoscStaffChange(b);
a[2].attach(m);
g.show;

img:: ![](../img/indicator-staff-change-1.png)
'''

p = "%/fosc/docs/img/indicator-staff-change-1".format(Platform.userExtensionDir);
g.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscStaffChange : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <staff;
    var <context='Staff', <formatLeafChildren=false, <formatSlot='opening', <timeOrientation='right';
    *new { |staff|
        assert(
            [FoscStaff, Nil].any { |type| staff.isKindOf(type) },
            "FoscStaffChange: can't initialize from: %".format(staff)
        );
        ^super.new.init(staff);
    }
    init { |argStaff|
        staff = argStaff;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context. Returns 'Staff'.


    • Example 1
    
    code::
    a = FoscStaffChange();
    a.context.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • staff

    Gets staff of staff change. Returns staff or nil.


    • Example 1
    
    code::
    a = FoscStaffChange();
    a.staff;


    • Example 2

    code::
    x = FoscStaff();
    a = FoscStaffChange(x);
    a.staff == x;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Tweaks are not implemented on staff change.
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of staff change.
    
    Returns string.


    • Example 1

    Default staff change.

    code::
    a = FoscStaffChange();
    a.str;


    • Example 2

    Explicit staff change.

    code::
    x = FoscStaff(name: 'LH');
    a = FoscStaffChange(x);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        if (staff.isNil) { ^"\\change Staff = ##f" };
        ^("\\change Staff = \"%\"".format(FoscScheme.formatSchemeValue(staff.name)));
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
        var bundle;
        bundle = FoscLilypondFormatBundle();
        bundle.opening.commands.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
