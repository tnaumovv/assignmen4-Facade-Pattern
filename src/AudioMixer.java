public class AudioMixer {
    private static final int MAX_AUDIO_BITRATE_KBPS = 160;
    private static final double NORMALIZED_AUDIO_PEAK_DB = -1.0;

    public ConversionResult normalizeAudio(ConversionResult media) {
        if (media == null) {
            throw new IllegalArgumentException("Conversion result is required.");
        }

        int audioBitrateKbps = Math.min(media.audioBitrateKbps(), MAX_AUDIO_BITRATE_KBPS);
        long estimatedSizeKb = estimateSizeKb(
                media.videoBitrateKbps(), audioBitrateKbps, media.durationSeconds());

        return media.withAudioSettings(
                audioBitrateKbps,
                NORMALIZED_AUDIO_PEAK_DB,
                estimatedSizeKb);
    }

    private long estimateSizeKb(int videoBitrateKbps, int audioBitrateKbps, int durationSeconds) {
        double kilobits = (videoBitrateKbps + (long) audioBitrateKbps) * durationSeconds;
        return (long) Math.ceil(kilobits / 8.0);
    }
}
