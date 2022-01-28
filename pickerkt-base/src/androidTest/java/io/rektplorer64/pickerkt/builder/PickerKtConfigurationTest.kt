package io.rektplorer64.pickerkt.builder

import android.os.Parcel
import io.rektplorer64.pickerkt.builder.query.operand.ContentColumn
import io.rektplorer64.pickerkt.builder.query.operand.valueOf
import io.rektplorer64.pickerkt.contentresolver.ContentResolverColumn
import io.rektplorer64.pickerkt.contentresolver.MimeType
import io.rektplorer64.pickerkt.contentresolver.Order
import org.junit.Assert.*
import org.junit.Test


class PickerKtConfigurationTest {
    @Test
    fun parcelableTest() {
        val testSubjectConfig = PickerKt.picker {
            allowMimes {
                add { MimeType.Jpeg }
                add { MimeType.Png }
                add { MimeType.Gif }
                add { MimeType.Svg }
                add { MimeType.Mpeg4 }
                add { MimeType.MsWordDoc2007 }
            }
        }

        // Obtain a Parcel object and write the parcelable object to it.
        val parcel = Parcel.obtain()
        testSubjectConfig.writeToParcel(parcel, 0)

        // After you're done with writing, you need to reset the parcel for reading
        parcel.setDataPosition(0)

        // Reconstruct object from parcel and asserts:
        val createdFromParcel: PickerKtConfiguration = PickerKtConfiguration.CREATOR.createFromParcel(parcel)
        assertEquals(testSubjectConfig, createdFromParcel)
    }

    @Test
    fun parcelableTest2() {
        val testSubjectConfig = PickerKt.picker {
            allowMimes {
                add { MimeType.Jpeg }
                add { MimeType.Png }
                add { MimeType.Gif }
                add { MimeType.Svg }
                add { MimeType.Mpeg4 }
                add { MimeType.MsWordDoc2007 }
            }
            selection {
                maxSelection(10)
                minSelection(8)
            }
            orderBy {
                add { Ordering(column = ContentResolverColumn.CollectionId, order = Order.Ascending) }
            }
            predicate {
                ContentColumn(ContentResolverColumn.ByteSize) greaterThan valueOf(0)
            }
        }

        val parcel = Parcel.obtain()
        testSubjectConfig.writeToParcel(parcel, 0)

        parcel.setDataPosition(0)

        val createdFromParcel: PickerKtConfiguration = PickerKtConfiguration.CREATOR.createFromParcel(parcel)
        assertEquals(testSubjectConfig, createdFromParcel)
    }
}