public class MediaConversionFacade {
    public ConversionResult convert(String fileName, String targetFormat) {
        MetadataReader metadataReader = new MetadataReader();
        CodecFactory codecFactory = new CodecFactory();
        BitrateReader bitrateReader = new BitrateReader();
        AudioMixer audioMixer = new AudioMixer();

        MediaMetadata metadata = metadataReader.read(fileName);
        CodecPair codecs = codecFactory.select(metadata.sourceFormat(), targetFormat);
        ConversionResult convertedMedia = bitrateReader.transcode(metadata, codecs);
        return audioMixer.normalizeAudio(convertedMedia);
    }
}
