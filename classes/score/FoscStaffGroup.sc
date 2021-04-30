/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscStaffGroup


SUMMARY:: Returns a FoscStaffGroup.


DESCRIPTION:: A staff group.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStaffGroup : FoscContext {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <defaultlilypondType='StaffGroup';
    *new { |music, lilypondType='StaffGroup', name, tag, playbackManager|
        ^super.new(music, lilypondType, true, name, tag, playbackManager);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prOnInsertionCheck

    Override and call corresponding method in superclass.

    '''
    code::
    a = FoscStaffGroup();
    a.append(FoscStaff());
    a.append(FoscVoice()); // raise exception
    '''
    -------------------------------------------------------------------------------------------------------- */
    prOnInsertionCheck { |index, node|
        if (node.isKindOf(FoscStaff).not) {
            throw("%: can't insert a % in this container.".format(this.species, node.species));
        };
        super.prOnInsertionCheck(index, node);
    }
}
