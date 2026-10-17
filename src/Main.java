public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equals(args[0])) {
            MediaConversionFacade facade = new MediaConversionFacade();
            ConversionResult result = facade.convert("sample.avi", "mp4");

            System.out.println("Conversion complete: " + result.fileName()
                    + " -> " + result.targetFormat()
                    + " | decoder=" + result.decoderName()
                    + " | encoder=" + result.encoderName()
                    + " | video=" + result.videoBitrateKbps() + " kbps"
                    + " | audio=" + result.audioBitrateKbps() + " kbps"
                    + " | peak=" + result.audioPeakDb() + " dB"
                    + " | estimated size=" + result.estimatedSizeKb() + " kB");
            return;
        }

        System.out.println("Usage: java -cp out Main --demo");
    }
}
