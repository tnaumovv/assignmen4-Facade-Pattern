public record MediaMetadata(
        String fileName,
        String sourceFormat,
        int durationSeconds,
        int sourceVideoBitrateKbps,
        int sourceAudioBitrateKbps,
        double audioPeakDb) {
}
