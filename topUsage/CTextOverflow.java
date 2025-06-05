package fe.cmn.text;

public enum CTextOverflow {
    /// Clip the overflowing text to fix its container.
    clip,

    /// Fade the overflowing text to transparent.
    fade,

    /// Use an ellipsis to indicate that the text has overflowed.
    ellipsis,

    /// Render overflowing text outside of its container.
    visible,
}
