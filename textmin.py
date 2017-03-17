from pydub import AudioSegment
from pydub.silence import split_on_silence
#wav -> 50
#mp3 -> 180
#sudo apt-get install libav-tools
#pip install pydub
sound_file = AudioSegment.from_mp3("news.mp3")
seg_len = len(sound_file)
print ("sample width is {:d}\n".format(sound_file.sample_width))
print ("frame rate is ", sound_file.frame_rate)
print ("channels ",sound_file.channels)
print ("length file is ",seg_len)
print ("max amplitude", sound_file.max_possible_amplitude)
audio_chunks = split_on_silence(sound_file,
    # must be silent for at least half a second
    min_silence_len=50,

    # consider it silent if quieter than -16 dBFS
    silence_thresh=-16
)
print (audio_chunks)
# for i, chunk in enumerate(audio_chunks):
#
#     out_file = ".//splitAudio//chunk{0}.mp3".format(i)
#     print ("exporting" .join(out_file))
#     chunk.export(out_file, format="mp3")
