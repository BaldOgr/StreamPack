/*
 * Copyright (C) 2021 Thibault B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.thibaultbee.streampack.utils

import android.content.Context
import android.util.Range
import android.util.Size
import com.github.thibaultbee.streampack.internal.sources.camera.getCameraFpsList
import com.github.thibaultbee.streampack.internal.sources.camera.getCameraOutputStreamSizes
import com.github.thibaultbee.streampack.streamers.bases.BaseCameraStreamer

/**
 * Configuration helper for [BaseCameraStreamer].
 * It wraps supported values from MediaCodec, Camera and TS Muxer.
 */
object CameraStreamerConfigurationHelper {
    object Video {
        /**
         * Supported encoders for a [BaseCameraStreamer]
         */
        val supportedEncoders = StreamerConfigurationHelper.Video.supportedEncoders

        /**
         * Get supported resolutions for a [BaseCameraStreamer].
         *
         * @param context application context
         * @param mimeType video encoder mime type
         * @return list of resolutions
         */
        fun getSupportedResolutions(context: Context, mimeType: String): List<Size> {
            val pair = StreamerConfigurationHelper.Video.getSupportedResolutions(mimeType)
            val codecSupportedWidths = pair.first
            val codecSupportedHeights = pair.second

            return context.getCameraOutputStreamSizes().filter {
                codecSupportedWidths.contains(it.width) && codecSupportedHeights.contains(it.height)
            }
        }

        /**
         * Get supported framerate for a [BaseCameraStreamer].
         *
         * @param context application context
         * @param mimeType video encoder mime type
         * @param cameraId camera id
         * @return list of framerates
         */
        fun getSupportedFramerates(
            context: Context,
            mimeType: String,
            cameraId: String
        ): List<Range<Int>> {
            val encoderFpsRange = StreamerConfigurationHelper.Video.getSupportedFramerate(mimeType)
            return context.getCameraFpsList(cameraId).filter { encoderFpsRange.contains(it) }
        }

        /**
         * Get supported bitrate range for a [BaseCameraStreamer].
         *
         * @param mimeType video encoder mime type
         * @return bitrate range
         */
        fun getSupportedBitrates(mimeType: String) =
            StreamerConfigurationHelper.Video.getSupportedBitrates(mimeType)
    }

    object Audio {
        /**
         * Get supported audio encoders list
         */
        val supportedEncoders = StreamerConfigurationHelper.Audio.supportedEncoders

        /**
         * Get maximum supported number of channel by encoder.
         *
         * @param mimeType audio encoder mime type
         * @return maximum number of channel supported by the encoder
         */
        fun getSupportedInputChannelRange(mimeType: String) =
            StreamerConfigurationHelper.Audio.getSupportedInputChannelRange(mimeType)

        /**
         * Get supported bitrate range for a [BaseCameraStreamer].
         *
         * @param mimeType audio encoder mime type
         * @return bitrate range
         */
        fun getSupportedBitrates(mimeType: String) =
            StreamerConfigurationHelper.Audio.getSupportedBitrates(mimeType)

        /**
         * Get audio supported sample rates.
         *
         * @param mimeType audio encoder mime type
         * @return sample rates list in Hz.
         */
        fun getSupportedSampleRates(mimeType: String): IntArray =
            StreamerConfigurationHelper.Audio.getSupportedSampleRates(mimeType)
    }
}