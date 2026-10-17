public record ConversionResult(
        String fileName,
        String sourceFormat,
        String targetFormat,
        String decoderName,
        String encoderName,
        int durationSeconds,
        int videoBitrateKbps,
        int audioBitrateKbps,
        double audioPeakDb,
        long estimatedSizeKb) {

    public ConversionResult withAudioSettings(
            int newAudioBitrateKbps,
            double newAudioPeakDb,
            long newEstimatedSizeKb) {
        return new ConversionResult(
                fileName,
                sourceFormat,
                targetFormat,
                decoderName,
                encoderName,
                durationSeconds,
                videoBitrateKbps,
                newAudioBitrateKbps,
                newAudioPeakDb,
                newEstimatedSizeKb);
    }
}
