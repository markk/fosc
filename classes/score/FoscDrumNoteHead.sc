/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscDrumNoteHead


SUMMARY:: Returns a FoscDrumNoteHead.


DESCRIPTION:: A drum note-head.


USAGE::

'''
FIXME ERROR: Can't initialize FoscPitch from value: snare

code::nointerpret
a = FoscDrumNoteHead('snare');
a.pitch;
'''
'''
code::nointerpret
FoscDrumNoteHead('foo');

post::
ERROR: Meta_FoscDrumNoteHead::new: name is not in LilypondDrums: foo.
'''
>>> note_head = scoretools.DrumNoteHead('snare')
>>> note_head
DrumNoteHead('snare')
------------------------------------------------------------------------------------------------------------ */
FoscDrumNoteHead : FoscNoteHead {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    *new { |writtenPitch='snare', client, isCautionary, isForced, isParenthesized, tweaks|
        var drumPitch;
        if (LilypondDrums.includes(writtenPitch.asSymbol).not) {
            throw("%::new: name is not in LilypondDrums: %.".format(this.species, writtenPitch));
        };
        drumPitch = LilypondDrums.at(writtenPitch.asSymbol);
        ^super.new(drumPitch, client, isCautionary, isForced, isParenthesized, tweaks);
    }
}
