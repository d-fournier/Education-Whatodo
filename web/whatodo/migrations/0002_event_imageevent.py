# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('whatodo', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='event',
            name='imageEvent',
            field=models.ImageField(default='static/images/jeanDujardin.jpg', upload_to='/static/images/'),
            preserve_default=True,
        ),
    ]
